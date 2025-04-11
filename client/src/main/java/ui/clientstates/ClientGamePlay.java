package ui.clientstates;
import ui.ClientMain;
import ui.EventLoop;
import ui.responcerecord.GameDataResponse;

import java.util.Scanner;

import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;

import static ui.EscapeSequences.*;

public class ClientGamePlay {
    // TODO: take out any excessive System.out calls
    private enum Orientation{
        BLACK_VIEW, WHITE_VIEW
    }

    public static void gameUI(){
        System.out.println(renderGame(pickOrientation())); // FIXME: may need to changes when the board is rendered to accommodate move highlighting
        System.out.print(SET_TEXT_COLOR_RED + "[Gameplay] >>> " + RESET_TEXT_COLOR);
        String command = scanner.next();
        switch(command.toLowerCase()){
            case "highlight":
                // TODO: Call shared valid moves method and render
                System.out.println("Hightlighting moves---DELETE ME");
                break;
            case "make":
                // TODO: Call API to make move or not if invalid
                System.out.println("Making move---DELETE ME");
                break;
            case "redraw":
                break; // UI redraws board after every command so breaking works the same way as redrawing
            case "leave":
                // TODO: remove payer name from the game
                eventState = EventLoop.EventState.LOGGEDIN;
                break;
            case "resign":
                // TODO: remove player from the game and finalize the game
                eventState = EventLoop.EventState.LOGGEDIN;
                break;
            case "help":
            case "h":
                System.out.println(GAMEPLAY_HELP_STRING);
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Please use one of the following commands...");
                System.out.println(GAMEPLAY_HELP_STRING);
                break;
        }

    }

    private static void makeMove(Scanner scanner){

    }

    private static String renderGame(Orientation orient){ // FIXME: Checkered pattern doesn't render properly on Black side
        StringBuilder strBuild = new StringBuilder();
        final int startIndex = orient == Orientation.WHITE_VIEW ? 0 : 7;
        final int increment = orient == Orientation.WHITE_VIEW ? 1 : -1;
        strBuild.append(SET_BG_COLOR_DARK_GREEN + "   ");
        for(int i = startIndex; i < 8 & i > -1; i += increment){
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(TOP_BOTTOM_BAR[i]);
        }
        strBuild.append("   " + RESET_BG_COLOR + "\n");
        for(int i = startIndex; i < 8 & i > -1; i+=increment){
            // Create one side of the board for indexing
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(LEFT_RIGHT_BAR[i]);
            strBuild.append(RESET_TEXT_COLOR);
            for(int j = 0; j < 8; j++){
                strBuild.append(i == 0 | i == 1 ? SET_TEXT_COLOR_BLUE : SET_TEXT_COLOR_RED);
                strBuild.append((i - j) % 2 == 0 ? SET_BG_COLOR_BLACK : SET_BG_COLOR_WHITE);
                strBuild.append(DEFAULT_BOARD[i][j]);
                strBuild.append(RESET_BG_COLOR + RESET_TEXT_COLOR);
            }
            // Create other side of the board of indexing
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(LEFT_RIGHT_BAR[i]);
            strBuild.append(RESET_TEXT_COLOR + RESET_BG_COLOR + "\n");
        }
        strBuild.append(SET_BG_COLOR_DARK_GREEN + "   ");
        for(int i = startIndex; i < 8 & i > -1; i += increment){
            strBuild.append(SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
            strBuild.append(TOP_BOTTOM_BAR[i]);
        }
        strBuild.append("   " + RESET_TEXT_COLOR + RESET_BG_COLOR);
        return strBuild.toString();
    }

    private static Orientation pickOrientation(){
        GameDataResponse currGameData = null;
        for(GameDataResponse game : ClientMain.currGameList){
            if(game.gameID() == ClientMain.activeGame){
                currGameData =  game;
                break;
            }
        }
        if(currGameData == null){
            return null;
        }
        if(ClientMain.userName.equals(currGameData.whiteUsername())) {
            return Orientation.WHITE_VIEW;
        } else if(ClientMain.userName.equals(currGameData.blackUsername())){
            return Orientation.BLACK_VIEW;
        }
        return Orientation.WHITE_VIEW;
    }

    private static final String[][] DEFAULT_BOARD = {
            {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
            {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN,BLACK_PAWN,BLACK_PAWN,BLACK_PAWN,BLACK_PAWN},
            {"   ","   ","   ","   ","   ","   ","   ","   "},
            {"   ","   ","   ","   ","   ","   ","   ","   "},
            {"   ","   ","   ","   ","   ","   ","   ","   "},
            {"   ","   ","   ","   ","   ","   ","   ","   "},
            {WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,},
            {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}
    };

    private static final String[] TOP_BOTTOM_BAR = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
    private static final String[] LEFT_RIGHT_BAR = { " 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "};

    static final String GAMEPLAY_HELP_STRING =
            SET_TEXT_COLOR_GREEN +
                    "redraw chess board" + RESET_TEXT_COLOR +  " - to redraw current chess board\n" +
                    SET_TEXT_COLOR_GREEN +
                    "make move <START> <END>" + RESET_TEXT_COLOR + " - to make a move\n" +
                    SET_TEXT_COLOR_GREEN +
                    "highlight legal moves <PIECE>" + RESET_TEXT_COLOR +
                        " - to highlight valid move for a given piece\n" +
                    SET_TEXT_COLOR_GREEN +
                    "resign" + RESET_TEXT_COLOR +  " - resign (forfeit) from the game\n" +
                    SET_TEXT_COLOR_GREEN +
                    "leave" + RESET_TEXT_COLOR +  " - leave the game (someone can take your place)\n" +
                    SET_TEXT_COLOR_GREEN +
                    "help | h" + RESET_TEXT_COLOR + " - list possible commands";
}
