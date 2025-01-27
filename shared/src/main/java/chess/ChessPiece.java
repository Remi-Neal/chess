package chess;

import chess.movesCalculator.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
// Generate Code -----------------------------------------
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }
    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
// --------------------------------------------------------------------------
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (this.type) {
            case KING -> {
                KingMovesCalculator calc = new KingMovesCalculator();
                return calc.pieceMoves(board, myPosition);
            }
            case QUEEN -> {
                QueenMoveCaculator calc = new QueenMoveCaculator();
                return calc.pieceMoves(board, myPosition);
            }
            case BISHOP -> {
                BishopMovesCalculator calc = new BishopMovesCalculator();
                return calc.pieceMoves(board, myPosition);
            }
            case ROOK -> {
                RookMovesCalculator calc = new RookMovesCalculator();
                return calc.pieceMoves(board, myPosition);
            }
            case KNIGHT -> {
                KnightMovesCalculator calc = new KnightMovesCalculator();
                return calc.pieceMoves(board, myPosition);
            }
            case PAWN -> {
                PawnMovesCalculator calc = new PawnMovesCalculator();
                return calc.pieceMoves(board, myPosition);
            }
            default -> throw new RuntimeException("Not implemented");
        }
    }
}
