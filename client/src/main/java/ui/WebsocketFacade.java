package ui;

import chess.ChessMove;
import websocket.commands.ConnectCommand;
import websocket.commands.UserGameCommand;
import websocket.commands.UserMoveCommand;
import websocket.commands.commandenums.PlayerTypes;

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
        URI uri = null;
        try {
            uri = new URI(wsUrl + "/ws");
        } catch (URISyntaxException e) {
            System.out.println("Unable to connect to server. Please try again.");
            return false;
        }
        if(uri != null) {
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
                        System.out.println(message);
                    }
                });
            }catch (Exception e){
                System.out.println("Unable to connect to server. Please try again.");
                return false;
            }
        }
        return true;
    }



    private static void writeCommand(UserGameCommand.CommandType commandType, String authToken, Integer gameID,
                                     PlayerTypes playerType, ChessMove move){
        if(playerType != null){
            // Create Connect command
            try {
                session.getBasicRemote().sendObject(new ConnectCommand(
                        commandType,
                        authToken,
                        gameID,
                        playerType
                ));
            } catch (Exception e) {
                handleErrors(e);
            }
        } else if(move != null){
            // Create move command
            try {
                session.getBasicRemote().sendObject(new UserMoveCommand(
                        commandType,
                        authToken,
                        gameID,
                        move
                ));
            } catch (Exception e) {
                handleErrors(e);
            }
        } else {
            // Create User Game command
            try {
                session.getBasicRemote().sendObject(new UserGameCommand(
                        commandType,
                        authToken,
                        gameID
                ));
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
