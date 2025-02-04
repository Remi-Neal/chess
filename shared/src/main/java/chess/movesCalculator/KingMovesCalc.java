package chess.movesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.movesCalculator.basic_moves.BasicMovesCalc;
import java.util.List;
import java.util.Arrays;

import java.util.ArrayList;

public class KingMovesCalc extends BasicMovesCalc {
    List<ChessMove> moves;
    public KingMovesCalc(){
        this.moves = new ArrayList<>();
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board, ChessPosition startingPosition) {
        List<Integer> directions =  Arrays.asList(-1,0,1);
        int row = startingPosition.getRow();
        int col = startingPosition.getColumn();
        for(int i : directions){
            for(int j : directions){
                if(i == 0 && j == 0) continue;
                ChessMove move = new ChessMove(startingPosition, new ChessPosition(row + i, col + j), null);
                tryAddMove(board, move);
            }
        }
        return this.moves;
    }

    @Override
    public void addMove(ChessMove move) {
        this.moves.add(move);
    }
}
