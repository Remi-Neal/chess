package ui;

import ui.responcerecord.GameDataResponse;

import java.util.ArrayList;

public class ClientMain {
    public static Integer port;
    public static ServerFacade serverFacade;
    public static String userName;
    public static String authToken;
    public static int activeGame;
    public static ArrayList<GameDataResponse> currGameList;
    public ClientMain(int portNum){
        port = portNum;
    }
    public static void main(String[] args){
        StringBuilder url = new StringBuilder("http://localhost:");
        if(port == null){
            port = 8080;
        }
        url.append(port);
        serverFacade = new ServerFacade(url.toString());
        var eventLoop = new EventLoop();
        eventLoop.run();
    }
}
