package chess.checkClasses;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class FindInterceptingPiece {
    public FindInterceptingPiece() {
    }

    public List<ChessPosition> findPath(ChessPosition attPiece, ChessPosition vicPiece){
        List<ChessPosition> path = new ArrayList<>();
        // Straight attack
        if(attPiece.getRow() == vicPiece.getRow()){
            int start = Math.min(attPiece.getColumn(), vicPiece.getColumn());
            int end = Math.max(attPiece.getColumn(), vicPiece.getColumn());
            while(start < end){
                start++;
                // TODO: Add col path calc
            }
        }
        if(attPiece.getColumn() == vicPiece.getColumn()){
            // TODO: Add row path calc
        }
        // Diag attack
        // TODO: Add diag path calc
        return path;
    }
    public List<ChessPosition> findPieces(ChessPosition attPiece, ChessPosition vicPiece, ChessBoard gameBoard) {
        ChessPiece.PieceType attPieceType = gameBoard.getPiece(attPiece).getPieceType();
        if(attPieceType == ChessPiece.PieceType.KING ||
                attPieceType == ChessPiece.PieceType.KNIGHT ||
                attPieceType == ChessPiece.PieceType.PAWN) return null; // Pieces cannot be blocked
        List<ChessPosition> attPath = findPath(attPiece, vicPiece);
        throw new RuntimeException("findPieces in FindInterseptingPiece.java not implemented");
        // TODO: Implement for loops to find pieces and paths to test if they can block attacking piece
    }

}
