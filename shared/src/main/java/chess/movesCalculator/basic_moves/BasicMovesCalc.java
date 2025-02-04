package chess.movesCalculator.basic_moves;

import chess.*;

import java.util.List;

public abstract class BasicMovesCalc {

    public BasicMovesCalc(){}

    public boolean outOfBounds(ChessPosition newPosition){
        if(
                newPosition.getRow() < 1 ||
                newPosition.getRow() > 8 ||
                newPosition.getColumn() < 1 ||
                newPosition.getColumn() > 8) return true;
        return false;
    }

    public boolean tryAddMove(ChessBoard board, ChessMove move){
        ChessPosition newPosition = move.getEndPosition();
        ChessGame.TeamColor pieceColor = board.getPiece(move.getStartPosition()).getTeamColor(); // ick

        if(outOfBounds(move.getEndPosition())) return false;
        if(board.getPiece(newPosition) == null){
            addMove(move);
            return true;
        }

        if(board.getPiece(newPosition).getTeamColor() == pieceColor) return false; // Blocked
        addMove(move); // Capture
        return false; // Valid move but cannot progress past point
    }

    abstract public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition);
    abstract public void addMove(ChessMove move);
}