package ui.clientstates.chessboard;

import chess.ChessBoard;
import websocket.commands.commandenums.PlayerTypes;

public class RenderBoard {
    private ChessBoard activeBoard;
    public void loadBoard(ChessBoard board){
        this.activeBoard = board;
    }
    public String renderBoard(PlayerTypes playerType){
        // TODO: Render Board
        if(activeBoard == null){
            System.out.println("No active board");
        }
        StringBuilder renderedBoard = new StringBuilder(activeBoard.toString());
        if(playerType == PlayerTypes.BLACK){
            // Render reversed board
        }
        return renderedBoard.toString();
    }
}
