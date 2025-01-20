package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.*;

import static chess.ChessPiece.PieceType.BISHOP;

public class BishopMovesCalculator extends PieceMovesCalculator {
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
