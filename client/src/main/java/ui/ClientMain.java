package ui;

public class ClientMain {
    public static int port;
    public static ServerFacade serverFacade;
    public ClientMain(int portNum){
        port = portNum;
    }
    public static void main(String[] args){
        StringBuilder url = new StringBuilder("http://localhost:");
        url.append(port);
        serverFacade = new ServerFacade(url.toString());
        var eventLoop = new EventLoop();
        eventLoop.run();
    }
}
