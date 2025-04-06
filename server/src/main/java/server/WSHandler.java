package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import websocket.commands.UserGameCommand;

@WebSocket
public class WSHandler {

    @OnWebSocketMessage
    public void onMessage(Session session, String json) throws Exception{
        var message = new Gson().fromJson(json, UserGameCommand.class);
        switch (message.getCommandType()){

        }
        System.out.printf("Received: %s", message);
        session.getRemote().sendString("WebSocket response: " + message);
    }
}
