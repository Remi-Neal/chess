package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator extends PieceMovesCalculator{
    List<ChessMove> moves;

    public KingMovesCalculator() {
        this.moves = new ArrayList<>();
    }

    @Override
    void addMove(ChessMove move) {
        this.moves.add(move);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(position).getTeamColor();

        ChessPosition newPosition;

        // To Row 8
        newPosition = new ChessPosition(row + 1, col);
        tryAddMove(board, pieceColor, position, newPosition);

        // To Row 1
        newPosition = new ChessPosition(row - 1, col);
        tryAddMove(board, pieceColor, position, newPosition);

        // To Col 8
        newPosition = new ChessPosition(row, col + 1);
        tryAddMove(board, pieceColor, position, newPosition);

        // To Col 1
        newPosition = new ChessPosition(row, col - 1);
        tryAddMove(board, pieceColor, position, newPosition);

        // To (8,8)
        newPosition = new ChessPosition(row + 1, col + 1);
        tryAddMove(board, pieceColor, position, newPosition);

        // To (1,8)
        newPosition = new ChessPosition(row - 1, col + 1);
        tryAddMove(board, pieceColor, position, newPosition);

        // To (8,1)
        newPosition = new ChessPosition(row + 1, col - 1);
        tryAddMove(board, pieceColor, position, newPosition);

        // To (1,1)
        newPosition = new ChessPosition(row - 1, col - 1);
        tryAddMove(board, pieceColor, position, newPosition);

        return this.moves;
    }
}
