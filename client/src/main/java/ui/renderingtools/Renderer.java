package ui.renderingtools;

import chess.ChessBoard;
import websocket.commands.commandenums.PlayerTypes;

public class Renderer {
    private ChessBoard activeBoard;
    public void loadBoard(ChessBoard board, PlayerTypes playerType){
        this.activeBoard = board;
        // TODO: Render Board
        if(activeBoard == null){
            System.out.println("No active board");
        } else {
            if (playerType == PlayerTypes.BLACK) {
                // Render reversed board
            }
            System.out.println("\r" + activeBoard);
        }
    }
    public String render(){
        return "";
    }
    public String renderBoard(PlayerTypes playerTypes){
        return "";
    }
}
