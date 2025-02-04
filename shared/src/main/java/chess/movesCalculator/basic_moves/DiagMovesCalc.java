package chess.movesCalculator.basic_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class DiagMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public DiagMovesCalc(){
        this.moves = new ArrayList<>();
    }
    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        // To (8,8)
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() + i, startingPosition.getColumn() + i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To (8,1)
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() + i, startingPosition.getColumn() - i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To (1,8)
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() - i, startingPosition.getColumn() + i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }
        // To (1,1)
        for(int i=1; i < 9; i++){
            ChessPosition newPosition = new ChessPosition(startingPosition.getRow() - i, startingPosition.getColumn() - i);
            if(tryAddMove(board, new ChessMove(startingPosition, newPosition, null))) continue;
            break;
        }

        return moves;
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }
}
