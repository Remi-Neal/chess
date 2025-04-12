package ui.clientstates.chessboard;

import chess.ChessBoard;

public class RenderBoard {
    private ChessBoard activeBoard;
    public void loadBoard(ChessBoard board){
        this.activeBoard = board;
    }
    public String renderBoard(){
        // TODO: Render Board
        StringBuilder renderedBoard = new StringBuilder(activeBoard.toString());

        return renderedBoard.toString();
    }
}
