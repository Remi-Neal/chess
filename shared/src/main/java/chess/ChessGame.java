package chess;

import chess.movesCalculator.KingMovesCalc;
import chess.movesCalculator.KnightMovesCalc;
import chess.movesCalculator.basic_moves.BasicMovesCalc;
import chess.movesCalculator.basic_moves.DiagMovesCalc;
import chess.movesCalculator.basic_moves.StraightMovesCalc;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard gameBoard;
    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.gameBoard = new ChessBoard();
        this.gameBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn=team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece selectedPiece = this.gameBoard.getPiece(startPosition);
        return selectedPiece.pieceMoves(this.gameBoard, startPosition);
    }

    /**
     *
     * @param move chess move to check
     * @throws InvalidMoveException if move is invalid
     */
    public void checkInvalidMoves(ChessMove move) throws InvalidMoveException{
        ChessPiece movedPiece = this.gameBoard.getPiece(move.getStartPosition());
        if(movedPiece == null){ throw new InvalidMoveException(); }
        if(teamTurn != movedPiece.getTeamColor()){ throw new InvalidMoveException(); }
        Collection<ChessMove> valid = validMoves(move.getStartPosition());
        if(!valid.contains(move)){ throw new InvalidMoveException();}

        if(isInCheck(teamTurn)){ throw new InvalidMoveException(); }
    }

    public ChessPosition findPiece(ChessBoard board, TeamColor teamColor, ChessPiece.PieceType pieceType){
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

    public ChessBoard makeNewBoard(ChessMove move, ChessBoard board){
        ChessPiece movedPiece = board.getPiece(move.getStartPosition());
        ChessBoard newBoard = new ChessBoard(board);
        newBoard.addPiece(move.getStartPosition(), null);
        if(movedPiece.getPieceType() == ChessPiece.PieceType.PAWN && move.getPromotionPiece() != null){
            newBoard.addPiece(move.getEndPosition(), new ChessPiece(this.teamTurn, move.getPromotionPiece()));
        }
        else{ newBoard.addPiece(move.getEndPosition(), movedPiece); }
        return newBoard;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        checkInvalidMoves(move);

        setBoard(makeNewBoard(move, this.gameBoard));
        this.teamTurn = this.teamTurn == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
    }

    public boolean pieceEqualPosition(ChessBoard board, ChessPosition position, TeamColor teamColor, ChessPiece.PieceType type){
        ChessPiece pieceCheck = board.getPiece(position);
        if(pieceCheck == null)  return false;
        return pieceCheck.getTeamColor() != teamColor && pieceCheck.getPieceType() == type;
    }

    public boolean isThreatened(TeamColor teamColor, ChessPosition piecePosition, ChessBoard board){ //TODO: Refactor to allow any piece be tested instead of just King

        // King test
        BasicMovesCalc checkMoves = new KingMovesCalc();
        Collection<ChessMove> squareChecks = checkMoves.getMoves(board, piecePosition);
        for(ChessMove move : squareChecks){
            ChessPiece pieceCheck = board.getPiece(move.getEndPosition());
            if(pieceCheck == null) continue;
            if(pieceCheck.getTeamColor() == teamColor ) continue;
            if(pieceCheck.getPieceType() == ChessPiece.PieceType.KING){
                return true;
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
                return true;
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
                return true;
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
                return true;
            }
        }

        // Pawn test
        int pawnDir = teamColor == TeamColor.WHITE ? 1 : -1;
        ChessPosition positionCheck = new ChessPosition(piecePosition.getRow() + pawnDir, piecePosition.getColumn() + 1);
        if(!checkMoves.outOfBounds(positionCheck)) {
            if (pieceEqualPosition(board, positionCheck, teamColor, ChessPiece.PieceType.PAWN)) return true;
        }
        positionCheck = new ChessPosition(piecePosition.getRow() + pawnDir, piecePosition.getColumn() - 1);
        if(!checkMoves.outOfBounds(positionCheck)) {
            if (pieceEqualPosition(board, positionCheck, teamColor, ChessPiece.PieceType.PAWN)) return true;
        }
        return false;
    }
    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isThreatened(teamColor, findPiece(this.gameBoard,teamColor, ChessPiece.PieceType.KING), this.gameBoard);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(!isInCheck(teamColor)) return false;
        //TODO: Test if threatening piece is threatened then check again

        BasicMovesCalc kingCalc = new KingMovesCalc();
        Collection<ChessMove> kingMoveOutOfCheck = kingCalc.getMoves(this.gameBoard,findPiece(this.gameBoard,teamColor, ChessPiece.PieceType.KING));
        Collection<ChessBoard> testBoards = new ArrayList<>();
        for(ChessMove move : kingMoveOutOfCheck){
            testBoards.add(makeNewBoard(move, this.gameBoard));
        }
        for(ChessBoard board : testBoards){
            if(!isThreatened(teamColor, findPiece(board,teamColor, ChessPiece.PieceType.KING),board)) return false;
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }
}
