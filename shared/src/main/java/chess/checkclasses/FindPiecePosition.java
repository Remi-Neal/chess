package chess.checkclasses;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class FindPiecePosition {
    public FindPiecePosition(){}


    public ChessPosition findPiece(ChessBoard board, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPiece piece = board.getPiece(new ChessPosition(i,j));
                if(piece == null) continue;
                if(piece.getPieceType() == pieceType && piece.getTeamColor() == teamColor){
                    return new ChessPosition(i,j);
                }
            }
        }
        throw new RuntimeException("Cannot find king");
    }
}
