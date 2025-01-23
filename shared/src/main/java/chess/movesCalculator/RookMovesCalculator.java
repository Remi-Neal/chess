package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator extends PieceMovesCalculator{
    private List<ChessMove> move;

    public RookMovesCalculator(){
        this.move = new ArrayList<>();
    }

    @Override
    void addMove(ChessMove move) {
        this.move.add(move);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(position).getTeamColor();

        for(int i = 1; i < 8; i++){
            // Towards row 8
            ChessPosition newPosition = new ChessPosition(row + i, col);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        for(int i = 1; i < 8; i++){
            // Towards row 1
            ChessPosition newPosition = new ChessPosition(row - i, col);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        for(int i = 1; i < 8; i++){
            // Towards col 8
            ChessPosition newPosition = new ChessPosition(row, col + i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        for(int i = 1; i < 8; i++){
            // Towards col 1
            ChessPosition newPosition = new ChessPosition(row, col - i);
            if(tryAddMove(board, pieceColor, position, newPosition)) continue;
            break;
        }

        return this.move;
    }
}
