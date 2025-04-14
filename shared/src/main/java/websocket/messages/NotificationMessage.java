package websocket.messages;

public class NotificationMessage extends ServerMessage{
    private String message;
    public NotificationMessage(ServerMessageType type, String notice) {
        super(type);
        this.message = notice;
    }
    public String getNotification(){ return this.message; }
}
