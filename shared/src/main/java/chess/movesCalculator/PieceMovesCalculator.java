package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

/**
 * Interface for piece specific calculators
 */
public interface PieceMovesCalculator {
    enum PositionStatus{
        OPEN,
        BLOCKED,
        CAPTURABLE,
        INECCESSIBLE,
    }
    default void addMove(ChessMove move) {}
    default Boolean tryAddMove(ChessBoard board, ChessGame.TeamColor color, ChessPosition position, ChessPosition newPosition) { return null; }
    default Boolean isOutOfBounds(ChessPosition position){ return null; }
    default PositionStatus isOccupied(ChessBoard board, ChessGame.TeamColor color, ChessPosition position) { return null; }
    /**
     * The calculator for all possible moves a given piece can make
     * @param board Current state of the game board.
     * @param position THe position of the piece we wish to calculate valid moves for.
     * @return A collection of all possible moves that piece can make
     */
    default Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return null;
    }

}
