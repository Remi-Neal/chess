package ui;

import chess.ChessMove;
import com.google.gson.Gson;
import ui.clientstates.chessboard.RenderBoard;
import websocket.commands.ConnectCommand;
import websocket.commands.UserGameCommand;
import websocket.commands.UserMoveCommand;
import websocket.commands.commandenums.PlayerTypes;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import websocket.messages.ServerMessage.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketFacade extends Endpoint {
    private final String wsUrl;
    private static Session session;

    public WebsocketFacade(String url) {
        this.wsUrl = "ws://" + url;
        session = null;
    }

    public boolean connectToServer(String authToken, Integer gameID, PlayerTypes playerTypes){
        URI uri;
        try {
            uri = new URI(wsUrl + "/ws");
        } catch (URISyntaxException e) {
            System.out.println("Unable to connect to server. Please try again.");
            return false;
        }
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(this, uri);
            writeCommand(
                    UserGameCommand.CommandType.CONNECT,
                    authToken,
                    gameID,
                    playerTypes,
                    null
            );
            session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    readMessage(message);
                }
            });
        }catch (Exception e){
            System.out.println("Unable to connect to server. Please try again.");
        return false;
        }
        return true;
    }

    public void leaveGame(String authToken, Integer gameID) throws IOException {
        writeCommand(
                UserGameCommand.CommandType.LEAVE,
                authToken,
                gameID,
                null,
                null
        );
        session.close();
    }

    private static void readMessage(String message){
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
        ServerMessage.ServerMessageType type = serverMessage.getServerMessageType();
        switch(type){
            case ServerMessageType.NOTIFICATION ->{
                handleNotification( new Gson().fromJson(message, NotificationMessage.class));
            }
            case ServerMessageType.LOAD_GAME -> {
                handleLoadGame(new Gson().fromJson(message, LoadGameMessage.class));
            }
            case ServerMessageType.ERROR -> {
                handleError(new Gson().fromJson(message, ErrorMessage.class));
            }
        }
    }

    private static void handleNotification(NotificationMessage message){
        System.out.println(message.getNotification());
    }
    private static void handleLoadGame(LoadGameMessage message){
        System.out.println("Load game called in wsFacade");
        ClientMain.renderer.loadBoard(message.getBoard(), ClientMain.playerType);
    }
    private static void handleError(ErrorMessage message){
        System.out.println(message.getErrorMessage());
    }

    private static void writeCommand(UserGameCommand.CommandType commandType, String authToken, Integer gameID,
                                     PlayerTypes playerType, ChessMove move){
        if(playerType != null){
            // Create Connect command
            try {
                ConnectCommand command = new ConnectCommand( // FIXME: This bit throws an error "Expected BEGIN_OBJECT but was STRING at line 1 column 1 path"
                        commandType,
                        authToken,
                        gameID,
                        playerType
                );
                session.getBasicRemote().sendText(new Gson().toJson(command));
            } catch (Exception e) {
                handleErrors(e);
            }
        } else if(move != null){
            // Create move command
            try {
                UserMoveCommand command = new UserMoveCommand(
                        commandType,
                        authToken,
                        gameID,
                        move
                );
                session.getBasicRemote().sendObject( new Gson().toJson(command));
            } catch (Exception e) {
                handleErrors(e);
            }
        } else {
            // Create User Game command
            try {
                UserGameCommand command = new UserGameCommand(
                        commandType,
                        authToken,
                        gameID
                );
                session.getBasicRemote().sendText( new Gson().toJson(command) );
            } catch (Exception e) {
                handleErrors(e);
            }
        }
    }

    private static void handleErrors(Exception e){
        System.out.println("An error occurred. Please try again.");
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
