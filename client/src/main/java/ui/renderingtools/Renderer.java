package ui.renderingtools;

import chess.ChessBoard;
import ui.ClientMain;
import ui.EventLoop;
import websocket.commands.commandenums.PlayerTypes;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;

public class Renderer {
    private ChessBoard activeBoard;
    public void loadBoard(ChessBoard board, PlayerTypes playerType){
        this.activeBoard = board;
        if(activeBoard == null){
            System.out.println("No active board");
        } else {
            renderBoard(PlayerTypes.WHITE);
        }
    }
    public static void render(String text){
        System.out.print("\r" + text + "\n" + addModeTag());
    }

    public void renderBoard(PlayerTypes playerTypes){
        //TODO: Render colorful board
        System.out.print("\r" + activeBoard + "\n" + addModeTag());
    }

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
