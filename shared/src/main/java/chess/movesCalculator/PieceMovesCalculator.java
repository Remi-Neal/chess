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

    /**
     * Wrapper method for addMoves. Checks if a given space is INECCESSIBLE, BLOCKED, CAPTURABLE, or OPEN.
     * This method is called by pieceMove which generates new moves for the method to test agnostic to the current piece.
     * The pieceMove method interprets the boolean value returned from tryAddMove
     *
     * @param board The current board layout.
     * @param color The current piece's color.
     * @param position The current position of the piece.
     * @param newPosition The new position to validate.
     * @return Boolean if more spaces can be tested.
     */
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

    /**
     * A method for checking if a given move falls outside the edge of the game board.
     * @param position The position to test.
     * @return A boolean true if the position is outside the board or false if it is still on the board
     */
    public Boolean isOutOfBounds(ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8;
    }
    public PositionStatus isOccupied(ChessBoard board, ChessGame.TeamColor color, ChessPosition position) {
        if(board.getPiece(position) == null ) return PositionStatus.OPEN;
        if(board.getPiece(position).getTeamColor() == color) { return PositionStatus.BLOCKED; }
        return PositionStatus.CAPTURABLE;
    }

    /**
     * Abstract method for addMove. addMove is implemented in child classes to add valid moves to a local 'moves' variable.
     * @param move A ChessMove to add.
     */
    abstract void addMove(ChessMove move);
    /**
     * The calculator for all possible moves a given piece can make
     * @param board Current state of the game board.
     * @param position THe position of the piece we wish to calculate valid moves for.
     * @return A collection of all possible moves that piece can make
     */
    abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}
