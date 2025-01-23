package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator extends PieceMovesCalculator{
    List<ChessMove> moves;

    public KnightMovesCalculator(){
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

        List<Integer> secondDirection = Arrays.asList(1,-1);

        for(int dir : secondDirection){
            // towards row 8
            ChessPosition newPosition = new ChessPosition(row + 2, col + dir);
            tryAddMove(board, pieceColor, position, newPosition);
        }

        for(int dir : secondDirection){
            // towards row 1
            ChessPosition newPosition = new ChessPosition(row - 2, col + dir);
            tryAddMove(board, pieceColor, position, newPosition);
        }

        for(int dir : secondDirection){
            // towards col 8
            ChessPosition newPosition = new ChessPosition(row + dir, col + 2);
            tryAddMove(board, pieceColor, position, newPosition);
        }

        for(int dir : secondDirection){
            // towards col 1
            ChessPosition newPosition = new ChessPosition(row + dir, col - 2);
            tryAddMove(board, pieceColor, position, newPosition);
        }

        return this.moves;
    }
}
