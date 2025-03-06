package chess.checkclasses;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movescalculator.*;
import chess.movescalculator.basicmoves.BasicMovesCalc;

import java.util.ArrayList;
import java.util.List;

public class FindInterceptingMoves {
    public FindInterceptingMoves() {
    }

    public BasicMovesCalc getCalc(ChessPiece piece){
        switch(piece.getPieceType()){
            case KING -> {
                return new KingMovesCalc();
            }
            case QUEEN -> {
                return new QueenMovesCalc();
            }
            case ROOK -> {
                return new RookMovesCalc();
            }
            case BISHOP -> {
                return new BishopMovesCalc();
            }
            case KNIGHT -> {
                return new KnightMovesCalc();
            }
            case PAWN -> {
                return new PawnMovesCalc();
            }
        }
        throw new RuntimeException("Unknown piece type in getCalc in FindInterceptingPiece.java");
    }

    public ChessPosition findIntersection(List<ChessPosition> path, List<ChessPosition> blocking){
        for(ChessPosition position : path){
            if(blocking.contains(position)) return position;
        }
        return null;
    }

    /**
     * Calculate the path from an attacking piece to a victim piece. Finds the positions in between the attacking piece and the victim piece
     * @param attPiece Attacking piece
     * @param vicPiece Victim piece
     * @return The positions in between the attacking and victim pieces
     */
    public List<ChessPosition> findPath(ChessPosition attPiece, ChessPosition vicPiece) {
        List<ChessPosition> path = new ArrayList<>();
        // Straight attack
        if (attPiece.getRow() == vicPiece.getRow()) {
            int start = Math.min(attPiece.getColumn(), vicPiece.getColumn()) + 1; // Adjust to only calculate positions in between
            int end = Math.max(attPiece.getColumn(), vicPiece.getColumn());
            while (start < end) {
                path.add(new ChessPosition(attPiece.getRow(), start));
                start++;
            }
        }
        if (attPiece.getRow() == vicPiece.getRow()) {
            int start = Math.min(attPiece.getRow(), vicPiece.getRow()) + 1; // Adjust to only calculate positions in between
            int end = Math.max(attPiece.getRow(), vicPiece.getRow());
            while (start < end) {
                path.add(new ChessPosition(attPiece.getRow(), start));
                start++;
            }
        }
        // Diag attack
        // Forward diag
        if ((attPiece.getColumn() > vicPiece.getColumn()) == (attPiece.getRow() > vicPiece.getRow())) { //True == True || False == False
            int startCol = Math.min(attPiece.getColumn(), vicPiece.getColumn()) + 1;
            int startRow = Math.min(attPiece.getRow(), vicPiece.getRow()) + 1; // Going from the furthest left to right, bottom to top
            int endCol = Math.max(attPiece.getColumn(), vicPiece.getColumn());
            int endRow = Math.max(attPiece.getRow(), vicPiece.getRow());
            while(startCol < endCol){ // Arbitrary could be rows too
                path.add(new ChessPosition(startRow,startCol));
                startCol++;
                startRow++;
            }
        }
        // TODO: Add Back slash diag
        // Back diag (second > switched to <)
        if ((attPiece.getColumn() > vicPiece.getColumn()) == (attPiece.getRow() < vicPiece.getRow())) { //True == True || False == False
            int startCol = Math.min(attPiece.getColumn(), vicPiece.getColumn()) + 1;
            int startRow = Math.max(attPiece.getRow(), vicPiece.getRow()) - 1; // Going from the furthest left to right, top to bottom
            int endCol = Math.max(attPiece.getColumn(), vicPiece.getColumn());
            int endRow = Math.min(attPiece.getRow(), vicPiece.getRow());
            while(startCol < endCol){ // Arbitrary could be rows too
                path.add(new ChessPosition(startRow,startCol));
                startCol++;
                startRow--;
            }
        }
        return path;
    }

    /**
     * Finds the chess pieces that can intercept or block an attacking piece from its victim.
     * @param attPiece The position of the attacking piece.
     * @param vicPiece The position of the victim piece.
     * @param gameBoard The game board to use
     * @return A list of the positions that can intercept the attacking piece
     */
    public List<ChessMove> findMoves(ChessPosition attPiece, ChessPosition vicPiece, ChessBoard gameBoard) {
        ChessPiece.PieceType attPieceType = gameBoard.getPiece(attPiece).getPieceType();
        if(attPieceType == ChessPiece.PieceType.KING ||
                attPieceType == ChessPiece.PieceType.KNIGHT ||
                attPieceType == ChessPiece.PieceType.PAWN) return null; // Pieces cannot be blocked
        List<ChessPosition> attPath = findPath(attPiece, vicPiece);

        List<ChessMove> blockingMoves = new ArrayList<>();
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = gameBoard.getPiece(position);
                if(piece == null) continue;
                BasicMovesCalc movesCalc = getCalc(piece);

                List<ChessPosition> endPositions = new ArrayList<>();
                for(ChessMove move : movesCalc.getMoves(gameBoard,position)){
                    endPositions.add(move.getEndPosition());
                }

                ChessPosition intersect = findIntersection(attPath, endPositions);
                if(intersect != null) blockingMoves.add(new ChessMove(position,intersect,null));
            }
        }

        return blockingMoves;
    }
}
