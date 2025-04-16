package ui.clientstates;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.ClientMain;
import ui.EventLoop;
import ui.renderingtools.Renderer;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;

import static ui.renderingtools.EscapeSequences.*;

public class ClientGamePlay {
    // TODO: take out any excessive System.out calls
    private enum Orientation{
        BLACK_VIEW, WHITE_VIEW
    }

    public static void gameUI(){
        String command = scanner.next();
        switch(command.toLowerCase()){
            case "highlight":
                // TODO: Call shared valid moves method and render
                highlightMoves(scanner);
                break;
            case "make":
                // TODO: Call API to make move or not if invalid
                makeMove(scanner);
                break;
            case "redraw":
                Renderer.renderBoard(ClientMain.playerType, null);
                break;
            case "leave":
                // TODO: remove payer name from the game
                leaveGame();
                break;
            case "resign":
                // TODO: remove player from the game and finalize the game
                resignFromGame(scanner);
                break;
            case "help":
            case "h":
                Renderer.renderText(GAMEPLAY_HELP_STRING);
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Please use one of the following commands...");
                System.out.println(GAMEPLAY_HELP_STRING);
                break;
        }

    }

    private static void resignFromGame(Scanner scanner){
        System.out.print(SET_TEXT_COLOR_RED + "Are you sure? Y|n >> ");
        String responce = scanner.next();
        if(Objects.equals(responce, "Y")){
            try {
                ClientMain.websocketFacade.resignFromGame(ClientMain.authToken, ClientMain.activeGame);
            } catch (IOException e) {
                System.out.println("Error resigning from game.");
            }
        }
        Renderer.renderText("Ok");
    }

    private static void leaveGame(){
        try {
            ClientMain.websocketFacade.leaveGame(ClientMain.authToken, ClientMain.activeGame);
            eventState = EventLoop.EventState.LOGGEDIN;
        } catch (IOException e) {
            System.out.println("Error leaving game.");
        }
    }

    private static ChessPosition positionConverter(String alphaNum) throws NumberFormatException{
        int row = Integer.parseInt(String.valueOf(alphaNum.charAt(1)));
        switch(alphaNum.charAt(0)){
            case 'a' -> {
                return new ChessPosition(row, 1);
            }
            case 'b' ->{
                return new ChessPosition(row, 2);
            }
            case 'c' -> {
                return new ChessPosition(row, 3);
            }
            case 'd' -> {
                return new ChessPosition(row, 4);
            }
            case 'e' -> {
                return new ChessPosition(row, 5);
            }
            case 'f' -> {
                return new ChessPosition(row, 6);
            }
            case 'g' -> {
                return new ChessPosition(row, 7);
            }
            case 'h' -> {
                return new ChessPosition(row, 8);
            }
            default -> {
                return new ChessPosition(1,1);
            }
        }
    }

    private static ChessPiece.PieceType promotionGenerator(String piece){
        switch(piece.toLowerCase()){
            case "queen" -> {
                return ChessPiece.PieceType.QUEEN;
            }
            case "bishop" -> {
                return ChessPiece.PieceType.BISHOP;
            }
            case "rook" -> {
                return ChessPiece.PieceType.ROOK;
            }
            case "knight" -> {
                return ChessPiece.PieceType.KNIGHT;
            }
            default -> {
                throw new RuntimeException("Unknown piece type: " + piece);
            }
        }
    }

    private static void makeMove(Scanner scanner){
        String[] moves = scanner.nextLine().split(" ");
        ChessPosition start;
        ChessPosition end;
        try {
            start = positionConverter(moves[2]);
            end = positionConverter(moves[3]);
        } catch (NumberFormatException e) {
            Renderer.renderText("Error: board index format should be in letter-number (a1) format");
            return;
        }
        ChessPiece.PieceType promotion = null;
        if(moves.length == 5) {
            try {
                ChessPiece.PieceType buffer = promotionGenerator(moves[4]);
                if(buffer != null) {
                    promotion = promotionGenerator(moves[4]);
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        ChessMove move = new ChessMove(start, end, promotion);
        ClientMain.websocketFacade.makeMove(ClientMain.authToken, ClientMain.activeGame, move);
    }

    private static void highlightMoves(Scanner scanner){
        String[] piece = scanner.nextLine().split(" ");
        Renderer.highlightBoard(ClientMain.playerType, positionConverter(piece[piece.length - 1]));
    }

    static final String GAMEPLAY_HELP_STRING =
            SET_TEXT_COLOR_GREEN +
                    "redraw chess board" + RESET_TEXT_COLOR +  " - to redraw current chess board\n" +
                    SET_TEXT_COLOR_GREEN +
                    "make move <START> <END>" + RESET_TEXT_COLOR + " - to make a move (e.x. make move b2 d2)\n" +
                    SET_TEXT_COLOR_GREEN +
                    "highlight legal moves <PIECE>" + RESET_TEXT_COLOR +
                        " - to highlight valid move for a given piece (e.x. highlight legal moves b2)\n" +
                    SET_TEXT_COLOR_GREEN +
                    "resign" + RESET_TEXT_COLOR +  " - resign (forfeit) from the game\n" +
                    SET_TEXT_COLOR_GREEN +
                    "leave" + RESET_TEXT_COLOR +  " - leave the game (someone can take your place)\n" +
                    SET_TEXT_COLOR_GREEN +
                    "help | h" + RESET_TEXT_COLOR + " - list possible commands";
}
