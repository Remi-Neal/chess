package chess.movesCalculator.basic_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class StraightMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;

    public StraightMovesCalc(){
        this.moves = new ArrayList<>();
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        // To row 8
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() + i, startingPosition.getColumn());
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To row 1
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() - i, startingPosition.getColumn());
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To column 8
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow(), startingPosition.getColumn() + i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To column 1
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow(), startingPosition.getColumn() - i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        return this.moves;
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }
}
