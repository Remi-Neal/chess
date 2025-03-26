package ui;

public class ServerFacade {
    public static int port;
    public ServerFacade(int portNum){
        port = portNum;
    }
    public static void main(String[] args){
        EventLoop.run();
    }
}
