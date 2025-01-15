package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.*;

import static chess.ChessPiece.PieceType.BISHOP;

public class BishopMovesCalculator implements PieceMovesCalculator {
    private List<ChessMove> moves;
    public BishopMovesCalculator() {
        this.moves = new ArrayList<>();
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }

    @Override
    public Boolean tryAddMove(ChessPosition position, ChessPosition newPosition) {
        if(!isOutOfBounds(newPosition)) {
            addMove(new ChessMove(newPosition, position, BISHOP));
            return true;
        }
        return false;
    }

    @Override
    public Boolean isOutOfBounds(ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        /*
        Create 4 loops to add and/or subtract values from rows and cols till they reach out of bounds
        Each loop adds their calculated position to the Collection<ChessMove>
         */

        int row = position.getRow();
        int col = position.getColumn();

        for (int i = 1; i < 8; i++) {
            // Towards A8
            boolean addedTowardsA8 = tryAddMove(position, new ChessPosition(row + i, col - i));
            // Towards H8
            boolean addedTowardsH8 = tryAddMove(position, new ChessPosition(row + i, col + i));
            // Towards H1
            boolean addedTowardsH1 = tryAddMove(position, new ChessPosition(row - i, col + i));
            // Towards A1
            boolean addedTowardsA1 = tryAddMove(position, new ChessPosition(row - i, col - i));

            if(addedTowardsA1 || addedTowardsA8 || addedTowardsH1 || addedTowardsH8) { continue; }
            break;
        }

        return this.moves;
    }
}
