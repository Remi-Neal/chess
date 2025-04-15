package ui.renderingtools;

import chess.*;
import ui.EventLoop;
import websocket.commands.commandenums.PlayerTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.*;

import static ui.EscapeSequences.*;

public class Renderer {
    private static ChessBoard activeBoard;

    public void loadBoard(ChessBoard board, PlayerTypes playerType){
        activeBoard = board;
        if(activeBoard == null){
            System.out.println("No active board");
        } else {
            renderBoard(playerType, null);
        }
    }

    public static void renderText(String text){
        System.out.print("\r" + text + "\n" + addModeTag());
    }

    public static void renderBoard(PlayerTypes playerType, List<ChessPosition> highlight){
        if(activeBoard == null){
            return;
        }
        if(highlight == null){
            highlight = new ArrayList<>();
        }
        StringBuilder strBuild = new StringBuilder("\r");
        final int startIndex = playerType == PlayerTypes.BLACK ? 7 : 0;
        final int increment = playerType == PlayerTypes.BLACK ? -1 : 1;

        // TOP BOARD
        strBuild.append(SET_BG_COLOR_DARK_GREEN);
        strBuild.append(EMPTY);
        for(int i = startIndex; i < 8 & i > -1; i += increment){
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(TOP_BOTTOM_BAR[i]);
        }
        strBuild.append("   " + RESET_BG_COLOR + "\n");

        // SIDES AND BOARD
        for(int i = startIndex; i < 8 & i > -1; i+=increment){
            // Create one side of the board for indexing
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(LEFT_RIGHT_BAR[i]);
            strBuild.append(RESET_TEXT_COLOR);
            for(int j = startIndex; j < 8 & j > -1; j+=increment){
                strBuild.append((i - j) % 2 == 0 ? SET_BG_COLOR_WHITE : SET_BG_COLOR_BLACK);
                if(highlight.contains(new ChessPosition(8-i,j+1))){
                    strBuild.append(SET_BG_COLOR_YELLOW);
                }
                strBuild.append(renderPiece(activeBoard.getPiece(new ChessPosition(8-i,j+1))));
                strBuild.append(RESET_BG_COLOR + RESET_TEXT_COLOR);
            }
            // Create other side of the board of indexing
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(LEFT_RIGHT_BAR[i]);
            strBuild.append(RESET_TEXT_COLOR + RESET_BG_COLOR + "\n");
        }
        strBuild.append(SET_BG_COLOR_DARK_GREEN + EMPTY);
        for(int i = startIndex; i < 8 & i > -1; i += increment){
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(TOP_BOTTOM_BAR[i]);
        }
        strBuild.append("   " + RESET_TEXT_COLOR + RESET_BG_COLOR);

        System.out.println(strBuild);
        System.out.print(addModeTag());
    }

    public static void highlightBoard(PlayerTypes playerTypes, ChessPosition position){
        ChessGame game = new ChessGame(WHITE, activeBoard); // White is arbitrary
        Collection<ChessMove> moves = game.validMoves(position);
        List<ChessPosition> positions = new ArrayList<>();
        for(ChessMove move : moves){
            positions.add(move.getEndPosition());
        }
        renderBoard(playerTypes, positions);
    }

    private static String addPieceColor(String piece, ChessGame.TeamColor color){
        if(color == ChessGame.TeamColor.WHITE){
            return SET_TEXT_COLOR_BLUE + piece + RESET_TEXT_COLOR;
        } else {
            return SET_TEXT_COLOR_RED + piece + RESET_TEXT_COLOR;
        }
    }

    private static String renderPiece(ChessPiece piece){
        if(piece == null) {
            return EMPTY;
        }
        switch (piece.getPieceType()){
            case KING -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_KING, WHITE) : addPieceColor(BLACK_KING, BLACK);
            }
            case QUEEN -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_QUEEN, WHITE) : addPieceColor(BLACK_QUEEN, BLACK);
            }
            case BISHOP -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_BISHOP, WHITE) : addPieceColor(BLACK_BISHOP, BLACK);
            }
            case KNIGHT -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_KNIGHT, WHITE) : addPieceColor(BLACK_KNIGHT, BLACK);
            }
            case ROOK -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_ROOK, WHITE) : addPieceColor(BLACK_ROOK, BLACK);
            }
            case PAWN -> {
                return piece.getTeamColor() == ChessGame.TeamColor.WHITE ?
                        addPieceColor(BLACK_PAWN, WHITE) : addPieceColor(BLACK_PAWN, BLACK);
            }
            default -> {
                return EMPTY;
            }
        }
    }

    private static final String[] TOP_BOTTOM_BAR = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
    private static final String[] LEFT_RIGHT_BAR = { " 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "};

    private static String addModeTag(){
        switch(EventLoop.eventState){
            case LOGGEDOUT -> {
            }
            case LOGGEDIN -> {
                return "";
            }
            case GAMEPLAY -> {
                return SET_TEXT_COLOR_RED + "[Gameplay] >>> " + RESET_TEXT_COLOR;
            }
        }
        return "";
    }
}
