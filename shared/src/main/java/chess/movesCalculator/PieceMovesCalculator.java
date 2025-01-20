package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

/**
 * Interface for piece specific calculators
 */
abstract public class PieceMovesCalculator {
    enum PositionStatus{
        OPEN,
        BLOCKED,
        CAPTURABLE,
        INECCESSIBLE,
    }

    public Boolean tryAddMove(ChessBoard board, ChessGame.TeamColor color, ChessPosition position, ChessPosition newPosition) {
        if(isOutOfBounds(newPosition)) return false;
        PositionStatus status = isOccupied(board,  color, newPosition);
        return switch (status) {
            case PositionStatus.OPEN -> {
                addMove(new ChessMove(position, newPosition, null));
                yield true;
            }
            case PositionStatus.CAPTURABLE -> {
                addMove(new ChessMove(position, newPosition, null));
                yield false;
            }
            case PositionStatus.BLOCKED -> false;
            default ->
                    throw new RuntimeException("Unknown position status. Expected: 'OPEN', 'BLOCKED', 'CAPTURABLE', or 'INECCESSIBLE'.");
        };
    }
    public Boolean isOutOfBounds(ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8;
    }
    public PositionStatus isOccupied(ChessBoard board, ChessGame.TeamColor color, ChessPosition position) {
        if(board.getPiece(position) == null ) return PositionStatus.OPEN;
        if(board.getPiece(position).getTeamColor() == color) { return PositionStatus.BLOCKED; }
        return PositionStatus.CAPTURABLE;
    }

    abstract void addMove(ChessMove move);
    /**
     * The calculator for all possible moves a given piece can make
     * @param board Current state of the game board.
     * @param position THe position of the piece we wish to calculate valid moves for.
     * @return A collection of all possible moves that piece can make
     */
    abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}
