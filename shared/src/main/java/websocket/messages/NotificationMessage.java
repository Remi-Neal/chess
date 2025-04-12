package websocket.messages;

public class NotificationMessage extends ServerMessage{
    private String notification;
    public NotificationMessage(ServerMessageType type, String notice) {
        super(type);
        this.notification = notice;
    }
    public String getNotification(){ return this.notification; }
}
