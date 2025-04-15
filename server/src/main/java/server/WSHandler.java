package server;

import chess.ChessBoard;
import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import datatypes.GameDataType;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import service.DAORecord;
import websocket.commands.UserGameCommand;
import websocket.commands.UserMoveCommand;
import service.gameservice.GameService;
import service.userservice.UserService;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.*;

@WebSocket
public class WSHandler {
    private record PlayerInfo(String authToken, String userName, Session session){}
    private final HashMap<Integer, List<PlayerInfo>> wsGameMap;
    private final UserService userService;
    private final GameService gameService;

    public WSHandler(DAORecord daoRecord){
        this.userService = new UserService(daoRecord);
        this.gameService = new GameService(daoRecord);
        this.wsGameMap = new HashMap<>();
        this.wsGameMap.put(-1, new ArrayList<>());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) throws Exception{
        var message = new Gson().fromJson(json, UserGameCommand.class);
        switch (message.getCommandType()){
            case MAKE_MOVE -> makeMove(session, new Gson().fromJson(json, UserMoveCommand.class));
            case CONNECT -> makeConnection(session, new Gson().fromJson(json, UserGameCommand.class));
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignFromGame(session, message);
            default -> session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                    ServerMessage.ServerMessageType.ERROR,
                    "ERROR: Unknown command"
            )));
        }
    }

    private void moveError(Session session, String extra) throws IOException {
        session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                ServerMessage.ServerMessageType.ERROR,
                "Error: Unable to make move " + extra
        )));
    }

    private void makeMove(Session session, UserMoveCommand moveCommand) throws IOException, DataAccessException {
        if(badCommand(moveCommand)){
            moveError(session, "bad command");
            return;
        }
        if(!wsGameMap.containsKey(moveCommand.getGameID())){
            moveError(session, "no game with id");
            return;
        }

        GameDataType game = gameService.getGame(moveCommand.getGameID());
        String username = userService.getUserName(moveCommand.getAuthToken());
        if(!game.active()){
            moveError(session, "inactive");
            return;
        }

        if(!(Objects.equals(username, game.blackUsername()) || Objects.equals(username, game.whiteUsername()))){
            moveError(session, "observer tried to make move");
            return;
        }
        if(game.chessGame().getTeamTurn() == ChessGame.TeamColor.BLACK
                & Objects.equals(username, game.whiteUsername())){
            moveError(session, "white tried to make move not on their turn");
            return;
        } else if(game.chessGame().getTeamTurn() == ChessGame.TeamColor.WHITE
                & Objects.equals(username, game.blackUsername())){
            moveError(session, "black tried to make move not on their turn. User name: " + username + " " + game.blackUsername() + " Team Turn: " + game.chessGame().getTeamTurn());
            return;
        }

        ChessBoard newBoard;
        try {
            newBoard = gameService.makeMove(moveCommand.getGameID(), moveCommand.getAuthToken(), moveCommand.getChessMove());
        } catch (InvalidMoveException e) {
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                    ServerMessage.ServerMessageType.ERROR,
                    "Error: invalid move"
            )));
            return;
        }

        List<String> disconnected = new ArrayList<>();
        for(PlayerInfo player: wsGameMap.get(moveCommand.getGameID())){
            if(!player.session.isOpen()){
                disconnected.add(player.authToken);
                continue;
            }
            if(!player.userName.equals(username)) {
                player.session.getRemote().sendString(new Gson().toJson(new NotificationMessage(
                        ServerMessage.ServerMessageType.NOTIFICATION,
                        username + " made a move"
                )));
            }
            player.session.getRemote().sendString(new Gson().toJson(new LoadGameMessage(
                    ServerMessage.ServerMessageType.LOAD_GAME,
                    newBoard
            )));
        }
        removeSessions(moveCommand.getGameID(), disconnected);
    }

    private boolean badCommand(UserGameCommand command){
        return !(
                gameService.validateGameID(command.getGameID()) &
                        userService.validateAuth(command.getAuthToken())
                );
    }
    private void makeConnection(Session session, UserGameCommand connectCommand) throws DataAccessException, IOException {
        String newUsersName = userService.getUserName(connectCommand.getAuthToken());
        if(badCommand(connectCommand)){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                    ServerMessage.ServerMessageType.ERROR,
                    "Error: Unable to join game"
            )));
            return;
        }
        if(!wsGameMap.containsKey(connectCommand.getGameID())){
            addNewGame(connectCommand.getGameID());
        }
        try {
            LoadGameMessage loadGame = new LoadGameMessage(
                    ServerMessage.ServerMessageType.LOAD_GAME,
                    gameService.getBoard(connectCommand.getGameID())
            );
            session.getRemote().sendString(new Gson().toJson(loadGame));
        } catch (Exception e){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                    ServerMessage.ServerMessageType.ERROR,
                    "Error: error loading game"
            )));
            return;
        }
        NotificationMessage notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                "'" + newUsersName + "' has joined the game");
        List<String> disconnected = new ArrayList<>();
        for(PlayerInfo player: wsGameMap.get(connectCommand.getGameID())){
            if(!player.session.isOpen()){
                disconnected.add(player.authToken);
                continue;
            }
            player.session.getRemote().sendString(new Gson().toJson(notification));
        }
        removeSessions(connectCommand.getGameID(), disconnected);
        wsGameMap.get(connectCommand.getGameID()).add(new PlayerInfo(
                connectCommand.getAuthToken(),
                newUsersName,
                session));
        if(connectCommand.getGameID() == null){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                    ServerMessage.ServerMessageType.ERROR,
                    "Error: no game selected"
            )));
        }
    }

    private void leaveGame(UserGameCommand userCommand) throws IOException, DataAccessException {
        String userName = userService.getUserName(userCommand.getAuthToken());
        removeSessions(userCommand.getGameID(), Collections.singletonList(userCommand.getAuthToken()));
        gameService.removePlayer(userCommand.getGameID(),userName);
        String message = "'" + userName + "' left the game. GOODBYE!";
        NotificationMessage notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                message);
        for(PlayerInfo player: wsGameMap.get(userCommand.getGameID())){
            player.session.getRemote().sendString(new Gson().toJson(notification));
        }
    }

    private void genericError(Session session) throws IOException {
        session.getRemote().sendString(new Gson().toJson(new ErrorMessage(
                ServerMessage.ServerMessageType.ERROR,
                "Error: Bad Command "
        )));
    }
    private void resignFromGame(Session session, UserGameCommand userCommand) throws DataAccessException, IOException {
        if(badCommand(userCommand)){
            genericError(session);
            return;
        }
        if(!wsGameMap.containsKey(userCommand.getGameID())){
            genericError(session);;
            return;
        }

        GameDataType game = gameService.getGame(userCommand.getGameID());
        String username = userService.getUserName(userCommand.getAuthToken());
        if(!game.active()){
            genericError(session);
            return;
        }

        if(!(Objects.equals(username, game.blackUsername()) || Objects.equals(username, game.whiteUsername()))) {
            genericError(session);
            return;
        }

            String userName = userService.getUserName(userCommand.getAuthToken());
        String message = "'" + userName + "' resigned.";
        NotificationMessage notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                message);
        for(PlayerInfo player: wsGameMap.get(userCommand.getGameID())){
            player.session.getRemote().sendString(new Gson().toJson(notification));
        }
        removeSessions(userCommand.getGameID(), Collections.singletonList(userCommand.getAuthToken()));
        gameService.closeGame(userCommand.getGameID());

    }


    private void addNewGame(Integer gameID){
        wsGameMap.put(gameID, new ArrayList<>());
    }

    private void removeSessions(Integer gameID, List<String> authTokens) throws IOException {
        List<PlayerInfo> updatedList = new ArrayList<>();
        for(PlayerInfo playerInfo : wsGameMap.get(gameID)){
            if(authTokens.contains(playerInfo.authToken)){
                playerInfo.session.disconnect();
                continue;
            }
            updatedList.add(playerInfo);
        }
        wsGameMap.put(gameID, updatedList);
    }
}
