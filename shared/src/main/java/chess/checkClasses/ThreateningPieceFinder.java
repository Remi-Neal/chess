package chess.checkClasses;

import chess.*;
import chess.movesCalculator.*;
import chess.movesCalculator.basic_moves.BasicMovesCalc;
import chess.movesCalculator.basic_moves.DiagMovesCalc;
import chess.movesCalculator.basic_moves.StraightMovesCalc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThreateningPieceFinder {
    ChessBoard gameBoard;
    public ThreateningPieceFinder(ChessBoard gameBoard){// TODO: see if this gameBoard is the same as ChessGame gameBoard
        this.gameBoard = gameBoard;
    }

    public List<ChessPosition> findPieces(ChessGame.TeamColor teamColor, ChessPosition piecePosition){
        return findPieces(teamColor, piecePosition, this.gameBoard);
    }

    public List<ChessPosition> findPieces(ChessGame.TeamColor teamColor, ChessPosition piecePosition, ChessBoard board){
        // TODO: refactor to help readability
        List<ChessPosition> threateningPieces = new ArrayList<>();
        // King test
        BasicMovesCalc checkMoves = new KingMovesCalc();
        Collection<ChessMove> squareChecks = checkMoves.getMoves(board, piecePosition);
        for(ChessMove move : squareChecks){
            ChessPiece pieceCheck = board.getPiece(move.getEndPosition());
            if(pieceCheck == null) continue;
            if(pieceCheck.getTeamColor() == teamColor ) continue;
            if(pieceCheck.getPieceType() == ChessPiece.PieceType.KING){
                threateningPieces.add(move.getEndPosition());
            }
        }

        // Bishop and Queen test
        checkMoves = new DiagMovesCalc();
        Collection<ChessMove> diagChecks = checkMoves.getMoves(board, piecePosition);
        for(ChessMove move : diagChecks){
            ChessPiece pieceCheck = board.getPiece(move.getEndPosition());
            if(pieceCheck == null) continue;
            if(pieceCheck.getTeamColor() == teamColor ) continue;
            if(pieceCheck.getPieceType() == ChessPiece.PieceType.BISHOP || pieceCheck.getPieceType() == ChessPiece.PieceType.QUEEN){
                threateningPieces.add(move.getEndPosition());
            }
        }

        // Rook and Queen test
        checkMoves = new StraightMovesCalc();
        Collection<ChessMove> strightChecks = checkMoves.getMoves(board, piecePosition);
        for(ChessMove move : strightChecks){
            ChessPiece pieceCheck = board.getPiece(move.getEndPosition());
            if(pieceCheck == null) continue;
            if(pieceCheck.getTeamColor() == teamColor ) continue;
            if(pieceCheck.getPieceType() == ChessPiece.PieceType.ROOK || pieceCheck.getPieceType() == ChessPiece.PieceType.QUEEN){
                threateningPieces.add(move.getEndPosition());
            }
        }

        // Knight test
        checkMoves = new KnightMovesCalc();
        Collection<ChessMove> knightChecks = checkMoves.getMoves(board, piecePosition);
        for(ChessMove move : knightChecks){
            ChessPiece pieceCheck = board.getPiece(move.getEndPosition());
            if(pieceCheck == null) continue;
            if(pieceCheck.getTeamColor() == teamColor ) continue;
            if(pieceCheck.getPieceType() == ChessPiece.PieceType.KNIGHT){
                threateningPieces.add(move.getEndPosition());
            }
        }

        // Pawn test
        int pawnDir = teamColor == ChessGame.TeamColor.WHITE ? 1 : -1;
        ChessPosition positionCheck = new ChessPosition(piecePosition.getRow() + pawnDir, piecePosition.getColumn() + 1);
        if(!checkMoves.outOfBounds(positionCheck)) {
            if (pieceEqualPosition(board, positionCheck, teamColor, ChessPiece.PieceType.PAWN)) threateningPieces.add(positionCheck);
        }
        positionCheck = new ChessPosition(piecePosition.getRow() + pawnDir, piecePosition.getColumn() - 1);
        if(!checkMoves.outOfBounds(positionCheck)) {
            if (pieceEqualPosition(board, positionCheck, teamColor, ChessPiece.PieceType.PAWN)) threateningPieces.add(positionCheck);
        }
        return threateningPieces;
    }

    public boolean pieceEqualPosition(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor, ChessPiece.PieceType type){
        ChessPiece pieceCheck = board.getPiece(position);
        if(pieceCheck == null)  return false;
        return pieceCheck.getTeamColor() != teamColor && pieceCheck.getPieceType() == type;
    }
}
