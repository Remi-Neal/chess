package chess.movescalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.movescalculator.basicmoves.BasicMovesCalc;
import chess.movescalculator.basicmoves.DiagMovesCalc;
import chess.movescalculator.basicmoves.StraightMovesCalc;

import java.util.ArrayList;
import java.util.List;

public class QueenMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public QueenMovesCalc(){
        this.moves = new ArrayList<>();
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        StraightMovesCalc straightMoves = new StraightMovesCalc();
        addMove(straightMoves.getMoves(board, startingPosition));
        DiagMovesCalc diagMoves = new DiagMovesCalc();
        addMove(diagMoves.getMoves(board, startingPosition));
        return moves;
    }

    public void addMove(List<ChessMove> moves){
        this.moves.addAll(moves);
    }
    @Override
    public void addMove(ChessMove move) {}
}
