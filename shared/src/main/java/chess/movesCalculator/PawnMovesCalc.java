package chess.movesCalculator;

import chess.*;
import chess.movesCalculator.basic_moves.BasicMovesCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PawnMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public PawnMovesCalc(){
        this.moves = new ArrayList<>();
    }

    public List<ChessMove> addPromotions(ChessMove move){
        List<ChessMove> promoted = new ArrayList<>();
        List<ChessPiece.PieceType> newTypes = Arrays.asList(
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT
        );

        for(ChessPiece.PieceType newType : newTypes){
            promoted.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), newType));
        }

        return promoted;
    }

    @Override
    public boolean tryAddMove(ChessBoard board, ChessMove move){
        ChessPosition newPosition = move.getEndPosition();
        ChessGame.TeamColor pieceColor = board.getPiece(move.getStartPosition()).getTeamColor(); // ick

        if(outOfBounds(move.getEndPosition())) return false;
        if(board.getPiece(newPosition) == null){
            if(newPosition.getRow() == 8 || newPosition.getRow() == 1){
                this.moves.addAll(addPromotions(move));
            }
            else addMove(move);
            return true;
        }

        return false; // Blocked by any piece in front of it
    }

    public void tryCapture(ChessBoard board, ChessMove move){
        ChessPosition newPosition = move.getEndPosition();
        ChessGame.TeamColor pieceColor = board.getPiece(move.getStartPosition()).getTeamColor(); // ick

        if(outOfBounds(move.getEndPosition()) || board.getPiece(newPosition) == null) return;

        if(board.getPiece(newPosition).getTeamColor() != pieceColor) {
            if(newPosition.getRow() == 8 || newPosition.getRow() == 1){
                this.moves.addAll(addPromotions(move));
            }
            else addMove(move);
        }
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        int row = startingPosition.getRow();
        int col = startingPosition.getColumn();
        int direction = board.getPiece(startingPosition).getTeamColor() == ChessGame.TeamColor.WHITE ? 1 : -1;

        // Basic move
        ChessMove move = new ChessMove(startingPosition, new ChessPosition(row + direction, col), null);
        if(tryAddMove(board, move)){
            // Two space starting move
            if((direction > 0 && row == 2) || (direction < 0 && row == 7)){
                move = new ChessMove(startingPosition, new ChessPosition(row + (direction * 2), col), null);
                tryAddMove(board, move);
            }
        }

        // Capture moves
        for(int i : Arrays.asList(-1,1)){
            move = new ChessMove(startingPosition, new ChessPosition(row + direction, col + i), null);
            tryCapture(board, move);
        }

        return this.moves;
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }
}
