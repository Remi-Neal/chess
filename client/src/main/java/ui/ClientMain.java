package ui;

public class ClientMain {
    public static int port;
    public ClientMain(int portNum){
        port = portNum;
    }
    public static void main(String[] args){
        EventLoop.run();
    }
}
