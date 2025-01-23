package chess.movesCalculator;

import chess.*;

import java.util.*;

public class PawnMovesCalculator extends PieceMovesCalculator{
    private final List<ChessMove> moves;

    public PawnMovesCalculator(){
        this.moves = new ArrayList<>();
    }

    @Override
    public void addMove(ChessMove move){
        this.moves.add(move);
    }

    public void addPromotions(ChessPosition position, ChessPosition newPosition){
        List<ChessPiece.PieceType> pieceTypes = Arrays.asList(
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.QUEEN);
        for(ChessPiece.PieceType type: pieceTypes){
            addMove(new ChessMove(position, newPosition, type));
        }
    }

    public boolean canPromote(ChessPosition newPosition){
        return newPosition.getRow() == 1 || newPosition.getRow() == 8;
    }

    public void tryAddCapture(ChessBoard board, ChessGame.TeamColor color, ChessPosition position, ChessPosition newPosition){
        if(isOutOfBounds(newPosition)) return;
        PositionStatus status = isOccupied(board,  color, newPosition);
        switch (status) {
            case PositionStatus.OPEN -> {
            }
            case PositionStatus.CAPTURABLE -> {
                if (canPromote(newPosition)) {
                    addPromotions(position, newPosition);
                } else {
                    addMove(new ChessMove(position, newPosition, null));
                }
            }
            case PositionStatus.BLOCKED -> {
            }
            default ->
                    throw new RuntimeException("Unknown position status. Expected: 'OPEN', 'BLOCKED', 'CAPTURABLE', or 'INECCESSIBLE'.");
        }
    }

    @Override
    public Boolean tryAddMove(ChessBoard board, ChessGame.TeamColor color, ChessPosition position, ChessPosition newPosition){
        if(isOutOfBounds(newPosition)) return false;
        PositionStatus status = isOccupied(board,  color, newPosition);
        return switch (status) {
            case PositionStatus.OPEN -> {
                if(canPromote(newPosition)){
                    addPromotions(position, newPosition);
                }
                else {
                    addMove(new ChessMove(position, newPosition, null));
                }
                yield true;
            }
            case PositionStatus.CAPTURABLE -> false;
            case PositionStatus.BLOCKED -> false;
            default ->
                    throw new RuntimeException("Unknown position status. Expected: 'OPEN', 'BLOCKED', 'CAPTURABLE', or 'INECCESSIBLE'.");
        };
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(position).getTeamColor();
        int direction = 1;
        if(pieceColor == ChessGame.TeamColor.BLACK){
            direction = -1;
        }

        // Forward 1 space
        ChessPosition newPosition = new ChessPosition(row + direction, col);
        Boolean added = tryAddMove(board, pieceColor, position, newPosition); // temp variable to handle boolean return

        // Move 2 from starting position
        if(added) {
            if (pieceColor == ChessGame.TeamColor.WHITE && row == 2) {
                newPosition = new ChessPosition(row + 2, col);
                tryAddMove(board, pieceColor, position, newPosition);
            }
            if (pieceColor == ChessGame.TeamColor.BLACK && row == 7) {
                newPosition = new ChessPosition(row - 2, col);
                tryAddMove(board, pieceColor, position, newPosition);
            }
        }
        // Captures on diagonal
        for(int diag : Arrays.asList(1,-1)) {
            newPosition = new ChessPosition(row + direction, col + diag);
            tryAddCapture(board, pieceColor, position, newPosition);
        }

        return this.moves;
    }
}
