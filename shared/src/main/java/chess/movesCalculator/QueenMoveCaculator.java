package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMoveCaculator extends PieceMovesCalculator{
    List<ChessMove> move;

    public QueenMoveCaculator(){
        this.move = new ArrayList<>();
    }

    @Override
    public void addMove(ChessMove move) {

    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        BishopMovesCalculator diagCalc = new BishopMovesCalculator();
        Collection<ChessMove> diagMoves = diagCalc.pieceMoves(board, position); // Handles diagonal motion
        RookMovesCalculator straightCalc = new RookMovesCalculator();
        Collection<ChessMove> straightMove = straightCalc.pieceMoves(board, position); // Handles vertical and horizontal motion

        this.move.addAll(diagMoves);
        this.move.addAll(straightMove);

        return this.move;
    }
}
