package chess;

import chess.checkClasses.FindPiecePosition;
import chess.checkClasses.ThreateningPieceFinder;
import chess.movesCalculator.KingMovesCalc;
import chess.movesCalculator.basic_moves.BasicMovesCalc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard gameBoard;
    private ThreateningPieceFinder threatPieceFinder;
    private FindPiecePosition findPiece;
    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.gameBoard = new ChessBoard();
        this.gameBoard.resetBoard();
        this.threatPieceFinder = new ThreateningPieceFinder(this.gameBoard);// TODO: see if this shares a reference to the same board object
        this.findPiece = new FindPiecePosition();
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

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return !threatPieceFinder.findPieces(
                teamColor,
                findPiece.findPiece(
                        this.gameBoard,
                        teamColor,
                        ChessPiece.PieceType.KING)).isEmpty();
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        List<ChessPosition> threateningPieces =
                threatPieceFinder.findPieces(
                        teamColor,
                        findPiece.findPiece(this.gameBoard,teamColor, ChessPiece.PieceType.KING),
                        this.gameBoard);
        if(threateningPieces.isEmpty()) return false; // Not in check

        //Single threatening piece is threatened
        if(threateningPieces.size() == 1) {
            TeamColor opponentColor = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
            List<ChessPosition> captureOutOfCheck =
                    threatPieceFinder.findPieces(opponentColor, threateningPieces.getFirst(),this.gameBoard);
            if(captureOutOfCheck.size() == 1){
                if(this.gameBoard.getPiece(captureOutOfCheck.getFirst()).getPieceType() == ChessPiece.PieceType.KING){
                    if(threatPieceFinder.findPieces(teamColor,captureOutOfCheck.getFirst(),this.gameBoard).isEmpty()) return false;
                }
            }
            else return false;
        }

        // Multiple threatening piece
        // Test if king can move out of check
        BasicMovesCalc kingCalc = new KingMovesCalc();
        Collection<ChessMove> kingMoveOutOfCheck = kingCalc.getMoves(
                this.gameBoard,findPiece.findPiece(this.gameBoard,teamColor, ChessPiece.PieceType.KING));
        Collection<ChessBoard> testBoards = new ArrayList<>();
        for(ChessMove move : kingMoveOutOfCheck){
            testBoards.add(makeNewBoard(move, this.gameBoard));
        }
        for(ChessBoard board : testBoards){
            if(threatPieceFinder.findPieces(
                    teamColor,
                    findPiece.findPiece(board,teamColor,
                            ChessPiece.PieceType.KING),board).isEmpty()) return false;
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
