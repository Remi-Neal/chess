package ui.clientstates.chessboard;

import chess.ChessBoard;
import websocket.commands.commandenums.PlayerTypes;

public class RenderBoard {
    private ChessBoard activeBoard;
    public void loadBoard(ChessBoard board, PlayerTypes playerType){
        System.out.println("RenderBoard: board loaded");
        this.activeBoard = board;
        // TODO: Render Board
        if(activeBoard == null){
            System.out.println("No active board");
        } else {
            if (playerType == PlayerTypes.BLACK) {
                // Render reversed board
            }
            System.out.println(activeBoard);
        }
    }
    public String render(PlayerTypes playerTypes){
        return "";
    }
}
