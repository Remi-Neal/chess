package chess.movescalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.movescalculator.basicmoves.BasicMovesCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public KnightMovesCalc(){
        this.moves = new ArrayList<>();
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        List<Integer> directions = Arrays.asList(-1,1);
        int row = startingPosition.getRow();
        int col = startingPosition.getColumn();
        for(int i : directions){ // first direction
            for(int j : directions){ // second direction
                ChessMove move = new ChessMove(startingPosition, new ChessPosition(row + (i*2), col + j), null);
                tryAddMove(board, move);
                move = new ChessMove(startingPosition, new ChessPosition(row + j, col +(i*2)), null);
                tryAddMove(board, move);
            }
        }
        return this.moves;
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }
}
