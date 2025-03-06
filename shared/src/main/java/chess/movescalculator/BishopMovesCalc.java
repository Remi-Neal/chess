package chess.movescalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.movescalculator.basicmoves.BasicMovesCalc;
import chess.movescalculator.basicmoves.DiagMovesCalc;

import java.util.ArrayList;
import java.util.List;

public class BishopMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public BishopMovesCalc(){
        this.moves = new ArrayList<>();
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        DiagMovesCalc diagMoves = new DiagMovesCalc();
        addMove(diagMoves.getMoves(board, startingPosition));
        return this.moves;
    }
    public void addMove(List<ChessMove> validMoves){
        this.moves.addAll(validMoves);
    }
    @Override
    public void addMove(ChessMove move) {}
}
