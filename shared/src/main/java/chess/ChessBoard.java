package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
// Generate Code -----------------------------------------
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
    @Override
    public String toString(){
        StringBuilder strBoard = new StringBuilder();
        for(int i = 7; i > -1; i--){
            for(int j = 7; j > -1; j--){
                ChessPiece piece = squares[i][j];
                if(piece == null) {
                    strBoard.append("| ");
                    continue;
                }
                switch(piece.getPieceType()){
                    case ChessPiece.PieceType.KING -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|K" : "|k");
                    case ChessPiece.PieceType.QUEEN -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|Q" : "|q");
                    case ChessPiece.PieceType.BISHOP -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|B" : "|b");
                    case ChessPiece.PieceType.KNIGHT -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|N" : "|n");
                    case ChessPiece.PieceType.ROOK -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|R" : "|r");
                    case ChessPiece.PieceType.PAWN -> strBoard.append(piece.getTeamColor() == ChessGame.TeamColor.WHITE ? "|P" : "|p");
                }
            }
            strBoard.append("|\n");
        }
        return strBoard.toString();
    }
// -----------------------------------------------------

    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
    }
    public ChessBoard(ChessBoard primeBoard){
        this.squares = primeBoard.squares;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece; // '-1' aligns zero indexing of java arrays to 1-8 of position
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1]; // '-1' aligns zero indexing of java arrays to 1-8 of position
    }

    public void resetPawns(ChessPiece[][] newBoard){
        for(int i = 0; i < 8; i++){
            newBoard[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        for(int i = 0; i < 8; i++){
            newBoard[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }
    }
    public void resetBackrows(ChessPiece[][] newBoard){
        int [] rows = {0,7};

        for(int row : rows){
            ChessGame.TeamColor color = row == 0 ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
            for(int i = 0; i < 8; i++){
                if(i==0 || i==7) {
                    newBoard[row][i] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
                    continue;
                }
                if(i==1 || i==6) {
                    newBoard[row][i] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
                    continue;
                }
                if(i==2 || i==5) {
                    newBoard[row][i] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
                    continue;
                }
                if(i==3) {
                    newBoard[row][i] = new ChessPiece(color, ChessPiece.PieceType.QUEEN);
                    continue;
                }
                newBoard[row][i] = new ChessPiece(color, ChessPiece.PieceType.KING);
            }
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessPiece[][] resetBoard = new ChessPiece[8][8];
        resetPawns(resetBoard);
        resetBackrows(resetBoard);
        this.squares = resetBoard;
    }
}
