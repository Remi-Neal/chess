package server;

import chess.ChessBoard;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import datatypes.GameDataType;
import datatypes.UserDataType;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import service.DAORecord;
import websocket.commands.ConnectCommand;
import websocket.commands.UserGameCommand;
import websocket.commands.UserMoveCommand;
import service.gameservice.GameService;
import service.userservice.UserService;
import websocket.commands.commandenums.PlayerTypes;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.*;

@WebSocket
public class WSHandler {
    private record PlayerInfo(String authToken, String userName, PlayerTypes playerType, Session session){}
    private HashMap<Integer, List<PlayerInfo>> wsGameMap;
    private UserService userService;
    private GameService gameService;

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
            case MAKE_MOVE -> {
                makeMove(session, new Gson().fromJson(json, UserMoveCommand.class));
                // TODO: Implement make move command
                break;
            }
            case CONNECT -> {
                makeConnection(session, new Gson().fromJson(json, ConnectCommand.class));

                // TODO: Implement connect
                break;
            }
            case LEAVE -> {
                leaveGame(session, message);
                // TODO: Implement leave
                break;
            }
            case RESIGN -> {
                resignFromGame(session, message);
                // TODO: Implement resign
                break;
            }
            default -> session.getRemote().sendString("ERROR: Unknown command");
        }
        System.out.printf("Received: %s", message);
        session.getRemote().sendString("WebSocket response: " + message);
    }

    private void makeMove(Session session, UserMoveCommand moveCommand){
        System.out.println("NOTICE: makeMove not implemented");
    }

    private void makeConnection(Session session, ConnectCommand connectCommand) throws DataAccessException, IOException {
        String newUsersName = userService.getUserName(connectCommand.getAuthToken());
        if(newUsersName == null){
            session.getRemote().sendString("Unable to join game");
        }
        if(!wsGameMap.containsKey(connectCommand.getGameID())){
            addNewGame(connectCommand.getGameID());
        }
        NotificationMessage notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                "'" + newUsersName + "' has joined the game as: " + connectCommand.getPlayerType());
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
                connectCommand.getPlayerType(),
                session));
        LoadGameMessage loadGame = new LoadGameMessage(
                ServerMessage.ServerMessageType.LOAD_GAME,
                gameService.getBoard(connectCommand.getGameID())
        );
        session.getRemote().sendString(new Gson().toJson(loadGame));
        notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                "TEST RESPONSE: joined game"
        );
        session.getRemote().sendString(new Gson().toJson(notification));
    }

    private void leaveGame(Session session, UserGameCommand userCommand) throws IOException, DataAccessException {
        String userName = userService.getUserName(userCommand.getAuthToken());
        String message = "'" + userName + "' left the game. GOODBYE!";
        NotificationMessage notification = new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                message);
        for(PlayerInfo player: wsGameMap.get(userCommand.getGameID())){
            player.session.getRemote().sendString(new Gson().toJson(notification));
        }
        removeSessions(userCommand.getGameID(), Collections.singletonList(userCommand.getAuthToken()));
        gameService.removePlayer(userCommand.getGameID(),userName);
    }

    private void resignFromGame(Session session, UserGameCommand userCommand){
        System.out.println("NOTICE: resignFromGame not implemented");
    }

    private void addNewGame(Integer gameID){
        wsGameMap.put(gameID, new ArrayList<PlayerInfo>());
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
