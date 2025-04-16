package chess;

import chess.checkclasses.FindInterceptingMoves;
import chess.checkclasses.FindPiecePosition;
import chess.checkclasses.ThreateningPieceFinder;
import chess.movescalculator.KingMovesCalc;
import chess.movescalculator.basicmoves.BasicMovesCalc;

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
    private ChessBoard game;
    private final ThreateningPieceFinder threatPieceFinder;
    private final FindPiecePosition findPiece;
    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.game = new ChessBoard();
        this.game.resetBoard();
        this.threatPieceFinder = new ThreateningPieceFinder(this.game);
        this.findPiece = new FindPiecePosition();
    }
    public ChessGame(TeamColor teamColor, ChessBoard game){
        this.teamTurn = teamColor;
        this.game = game;
        this.threatPieceFinder = new ThreateningPieceFinder(game);
        this.findPiece = new FindPiecePosition();
    }

    public ChessGame copyChessGame(){
        return new ChessGame(this.getTeamTurn(), this.game);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    public ChessPiece getPiece(ChessPosition position){
        return this.game.getPiece(position);
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
        ChessPiece selectedPiece = this.game.getPiece(startPosition);
        List<ChessMove> moves = (List<ChessMove>) selectedPiece.pieceMoves(this.game, startPosition);
        List<ChessMove> valid = new ArrayList<>();
        for(ChessMove move : moves){
            ChessBoard newBoard = makeNewBoard(move,this.game);
            if(selectedPiece.getPieceType() == ChessPiece.PieceType.KING){
                if(!threatPieceFinder.isThreatened(
                        selectedPiece.getTeamColor(),
                        move.getEndPosition(),
                        newBoard)){
                    valid.add(move);
                }
            } else {
                if(!threatPieceFinder.isThreatened(
                        selectedPiece.getTeamColor(),
                        findPiece.findPiece(newBoard,selectedPiece.getTeamColor(), ChessPiece.PieceType.KING),
                        newBoard)){
                    valid.add(move);
                }
            }
        }
        return valid;
    }

    /**
     *
     * @param move chess move to check
     * @throws InvalidMoveException if move is invalid
     */
    public void checkInvalidMoves(ChessMove move) throws InvalidMoveException{
        ChessPiece movedPiece = this.game.getPiece(move.getStartPosition());
        if(movedPiece == null){ throw new InvalidMoveException(); }
        if(teamTurn != movedPiece.getTeamColor()){ throw new InvalidMoveException(); }
        Collection<ChessMove> valid = validMoves(move.getStartPosition());
        boolean validMove = false;
        for(ChessMove validM : valid){
            if(validM.getEndPosition().getRow() == move.getEndPosition().getRow() &&
                validM.getEndPosition().getColumn() == move.getEndPosition().getColumn()){
                validMove = true;
                break;
            }
        }
        if(!validMove){ throw new InvalidMoveException(); }
        // Promotion Checks
        if(move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1){
            if(game.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN
                    & move.getPromotionPiece() == null){
                throw new InvalidMoveException();
            }
        }
        if(move.getPromotionPiece() != null){
            if(game.getPiece(move.getStartPosition()).getPieceType() != ChessPiece.PieceType.PAWN){
                throw new InvalidMoveException();
            }
            if(move.getEndPosition().getRow() != 8 & move.getEndPosition().getRow() != 1){
                throw new InvalidMoveException();
            }
        }
    }

    /**
     * Makes a new chess board based on give board and given move
     * @param move Move to add to the board
     * @param board Any game board
     * @return A new chess board with move added
     */
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

        setBoard(makeNewBoard(move, this.game));
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
                        this.game,
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
        ChessPosition kingPosition = findPiece.findPiece(this.game,teamColor, ChessPiece.PieceType.KING);
        List<ChessPosition> threateningPieces =
                threatPieceFinder.findPieces(
                        teamColor,
                        kingPosition,
                        this.game);
        if(threateningPieces.isEmpty()){ return false; }// Not in check

        //Single threatening piece is threatened
        if(threateningPieces.size() == 1) {
            TeamColor opponentColor = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
            List<ChessPosition> captureOutOfCheck =
                    threatPieceFinder.findPieces(opponentColor, threateningPieces.getFirst(),this.game);

            // Capture threatening piece
            if(captureOutOfCheck.size() == 1){
                if(this.game.getPiece(captureOutOfCheck.getFirst()).getPieceType() == ChessPiece.PieceType.KING){
                    if(threatPieceFinder.findPieces(teamColor,captureOutOfCheck.getFirst(),this.game).isEmpty()){
                        return false;
                    }
                }
            } else if (captureOutOfCheck.size() > 1) {
                return false;
            }

            // Block threatening piece
            FindInterceptingMoves calcBlock = new FindInterceptingMoves();
            List<ChessMove> blockingMoves = calcBlock.findMoves(
                    threateningPieces.getFirst(),
                    kingPosition,this.game);
            if(blockingMoves != null) {
                for(ChessMove move : blockingMoves){
                    ChessBoard newBoard = makeNewBoard(move, this.game);
                    if(threatPieceFinder.findPieces(
                            teamColor,
                            findPiece.findPiece(newBoard,teamColor, ChessPiece.PieceType.KING),
                            newBoard).isEmpty()){
                        return false;
                    }
                }
            }
        }

        // Multiple threatening piece
        // Test if king can move out of check
        BasicMovesCalc kingCalc = new KingMovesCalc();
        Collection<ChessMove> kingMoveOutOfCheck = kingCalc.getMoves(
                this.game,findPiece.findPiece(this.game,teamColor, ChessPiece.PieceType.KING));
        Collection<ChessBoard> testBoards = new ArrayList<>();
        for(ChessMove move : kingMoveOutOfCheck){
            testBoards.add(makeNewBoard(move, this.game));
        }
        for(ChessBoard board : testBoards){
            if(threatPieceFinder.findPieces(
                    teamColor,
                    findPiece.findPiece(board,teamColor,
                            ChessPiece.PieceType.KING),board).isEmpty()){ return false; }
        }
        return true;
    }

    private boolean checkSquareForLegalMove(TeamColor teamColor, int i, int j){
        ChessPiece piece = game.getPiece(new ChessPosition(i,j));
        if(piece == null){ return true; }
        if(piece.getTeamColor() != teamColor){ return true; }

        FindInterceptingMoves getCalc = new FindInterceptingMoves();
        BasicMovesCalc calc = getCalc.getCalc(piece);
        List<ChessMove> moves = calc.getMoves(this.game, new ChessPosition(i,j));
        if(moves.isEmpty()){ return true; }

        // If King is it in check after moving
        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            for(ChessMove move : moves){
                ChessBoard newBoard = makeNewBoard(move, this.game);
                if(threatPieceFinder.findPieces(teamColor,move.getEndPosition(),newBoard).isEmpty()){
                    return false;
                }
            }
            return true;
        }

        for(ChessMove move : moves){
            ChessBoard newBoard = makeNewBoard(move, this.game);
            if(!threatPieceFinder.findPieces(
                    teamColor,
                    findPiece.findPiece(newBoard, teamColor, ChessPiece.PieceType.KING),
                    newBoard).isEmpty()){
                continue;
            }
            return false;
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
        if(isInCheck(teamColor)){ return false; }
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(checkSquareForLegalMove(teamColor, i, j)){ continue; }
                return false;
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.game = board;
        this.threatPieceFinder.setGameBoard(board);
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return game;
    }
}
