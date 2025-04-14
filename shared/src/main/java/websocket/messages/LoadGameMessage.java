package websocket.messages;

import chess.ChessBoard;

public class LoadGameMessage extends ServerMessage{
    ChessBoard game;
    public LoadGameMessage(ServerMessageType type, ChessBoard game) {
        super(type);
        this.game = game;
    }
    public ChessBoard getBoard(){ return this.game; }
}
