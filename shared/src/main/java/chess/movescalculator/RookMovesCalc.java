package chess.movescalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.movescalculator.basicmoves.BasicMovesCalc;
import chess.movescalculator.basicmoves.StraightMovesCalc;

import java.util.ArrayList;
import java.util.List;

public class RookMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public RookMovesCalc(){
        this.moves = new ArrayList<>();
    }
    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {

        StraightMovesCalc straightMoves = new StraightMovesCalc();
        addMove(straightMoves.getMoves(board, startingPosition));
        return this.moves;
    }
    public void addMove(List<ChessMove> validMoves){
        this.moves.addAll(validMoves);
    }
    @Override
    public void addMove(ChessMove move) {

    }
}
