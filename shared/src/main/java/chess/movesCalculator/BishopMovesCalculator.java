package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.*;

import static chess.ChessPiece.PieceType.BISHOP;

public class BishopMovesCalculator implements PieceMovesCalculator {
    private List<ChessMove> moves;

    public BishopMovesCalculator() {
        this.moves = new ArrayList<>();
    }

    /**
     * A setter method to add a valid move to a list of possible chess moves for a bishop
     * @param move a move to add to the list of possible moves
     */
    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }

    /**
     * A wrapper class for addMove to validate if a move is valid.
     * @param position The current position the bishop piece.
     * @param newPosition The new position we wish to validate.
     * @return A boolean value. True to continue testing positions and false if an obstruction is met.
     */
    @Override
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
        // TODO: add capture functionality canCapture(board, color, ChessPosition
        // TODO: add same color blocking to parent class isBlocked(board, color, ChessPosition)
    }

    /**
     * A method to calculate if a given position is out of bounds.
     * @param position The position to check.
     * @return A boolean value. False if the piece is within bounds and true if it is out of bounds.
     */
    @Override
    public Boolean isOutOfBounds(ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8;
    }

    /**
     * A method to calculate if a given position is blocked. Adds blocked position to a list of blocked positions
     * @param board The current game board.
     * @param color The current bishop's color.
     * @param position The position to check.
     * @return An enum Position status: OPEN, BLOCKED, CAPTURABLE
     */
    @Override
    public PositionStatus isOccupied(ChessBoard board, ChessGame.TeamColor color, ChessPosition position) {
        if(board.getPiece(position) == null ) return PositionStatus.OPEN;
        if(board.getPiece(position).getTeamColor() == color) { return PositionStatus.BLOCKED; }
        return PositionStatus.CAPTURABLE;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        /*
        Create 4 loops to add and/or subtract values from rows and cols till they reach out of bounds
        Each loop adds their calculated position to the Collection<ChessMove>
         */

        int row = position.getRow();
        int col = position.getColumn();
        // TODO: add code to get piece's color
        ChessGame.TeamColor pieceColor = board.getPiece(position).getTeamColor();

        for (int i = 1; i < 8; i++) {
            // Towards (8,1)
            ChessPosition newPosition = new ChessPosition(row + i, col - i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        for (int i = 1; i < 8; i++) {
            // Towards (8,8)
            ChessPosition newPosition = new ChessPosition(row + i, col + i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }
        for (int i = 1; i < 8; i++) {
            // Towards (1,8)
            ChessPosition newPosition = new ChessPosition(row - i, col + i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }
        for (int i = 1; i < 8; i++) {
            // Towards (1,1)
            ChessPosition newPosition = new ChessPosition(row - i, col - i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        return this.moves;
    }
}
