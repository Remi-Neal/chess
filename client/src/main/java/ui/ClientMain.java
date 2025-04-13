package ui;

import ui.clientstates.chessboard.Renderer;
import ui.responcerecord.GameDataResponse;
import websocket.commands.commandenums.PlayerTypes;

import java.util.ArrayList;

public class ClientMain {
    public static Integer port;
    public static ServerFacade serverFacade;
    public static WebsocketFacade websocketFacade;
    public static String userName;
    public static String authToken;
    public static PlayerTypes playerType;
    public static int activeGame;
    public static Renderer renderer;
    public static ArrayList<GameDataResponse> currGameList;

    public ClientMain(int portNum){
        port = portNum;
    }

    public static void main(String[] args){
        StringBuilder url = new StringBuilder("localhost:");
        if(port == null){
            port = 8080;
        }
        url.append(port);
        serverFacade = new ServerFacade(url.toString());
        websocketFacade = new WebsocketFacade(url.toString());
        renderer = new Renderer();
        var eventLoop = new EventLoop();
        eventLoop.run();
    }
}
