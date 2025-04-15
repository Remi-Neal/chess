package ui;

import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.commands.UserMoveCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import websocket.messages.ServerMessage.*;

import static ui.ClientMain.*;
import static ui.renderingtools.Renderer.render;

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

    public boolean connectToServer(String authToken, Integer gameID){
        URI uri;
        try {
            uri = new URI(wsUrl + "/ws");
        } catch (URISyntaxException e) {
            System.out.println("Error: Unable to connect to server. Please try again.");
            return false;
        }
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(this, uri);
            writeCommand(
                    UserGameCommand.CommandType.CONNECT,
                    authToken,
                    gameID,
                    null
            );
            session.addMessageHandler((MessageHandler.Whole<String>) WebsocketFacade::readMessage);
        }catch (Exception e){
            System.out.println("Error: Unable to connect to server. Please try again.");
        return false;
        }
        return true;
    }

    public void leaveGame(String authToken, Integer gameID) throws IOException {
        writeCommand(
                UserGameCommand.CommandType.LEAVE,
                authToken,
                gameID,
                null
        );
        session.close();
    }

    public void resignFromGame(String authToken, Integer gameID) throws IOException {
        writeCommand(
                UserGameCommand.CommandType.RESIGN,
                authToken,
                gameID,
                null
        );
        session.close();
    }

    public void makeMove(String authToken, Integer gameID, ChessMove move){
        writeCommand(
                UserGameCommand.CommandType.MAKE_MOVE,
                authToken,
                gameID,
                move
        );
    }

    private static void readMessage(String message){
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
        ServerMessage.ServerMessageType type = serverMessage.getServerMessageType();
        switch(type){
            case ServerMessageType.NOTIFICATION -> handleNotification( new Gson().fromJson(message, NotificationMessage.class));
            case ServerMessageType.LOAD_GAME -> handleLoadGame(new Gson().fromJson(message, LoadGameMessage.class));
            case ServerMessageType.ERROR -> handleError(new Gson().fromJson(message, ErrorMessage.class));
        }
    }

    private static void handleNotification(NotificationMessage message){
        render(message.getNotification());
    }
    private static void handleLoadGame(LoadGameMessage message){
        renderer.loadBoard(message.getBoard(), playerType);
    }
    private static void handleError(ErrorMessage message){
        render(message.getErrorMessage());
    }

    private static void writeCommand(UserGameCommand.CommandType commandType, String authToken, Integer gameID,
                                     ChessMove move) {
        // Create Connect command
        if(move != null) {
            // Create move command
            try {
                UserMoveCommand command = new UserMoveCommand(
                        commandType,
                        authToken,
                        gameID,
                        move
                );
                session.getBasicRemote().sendObject(new Gson().toJson(command));
            } catch (Exception e) {
                handleErrors(e);
            }
            return;
        }
        // Create User Game command
        try {
            UserGameCommand command = new UserGameCommand(
                    commandType,
                    authToken,
                    gameID
            );
            session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (Exception e) {
            handleErrors(e);
        }
    }

    private static void handleErrors(Exception e){
        System.out.println("An error occurred. Please try again.");
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
