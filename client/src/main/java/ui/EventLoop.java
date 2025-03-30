package ui;

import ui.clientstates.ClientGamePlay;
import ui.clientstates.ClientLoggedIn;
import ui.clientstates.ClientLoggedOut;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class EventLoop {
    public static Scanner scanner;
    public static EventState eventState;
    public EventLoop(){
        scanner = new Scanner(System.in);
        eventState = EventState.LOGGEDOUT;
    }
    public  void run(){
        System.out.print("👸 WELCOME to Chess a la BYU CS 240. ");
        System.out.print("Type ");
        System.out.print(SET_TEXT_BLINKING + "HELP" + RESET_TEXT_BLINKING);
        System.out.print(" to begin! 🤴\n");
        boolean runLoop = true;
        while(runLoop){
            switch(eventState){
                case LOGGEDOUT -> ClientLoggedOut.loggedOutUI();
                case LOGGEDIN -> ClientLoggedIn.loggedinUI();
                case GAMEPLAY -> ClientGamePlay.gameUI();
                case QUIT -> {
                    System.out.println("GOODBY! 👋");
                    runLoop = false;
                }
            }
        }
    }

    public enum EventState{
        LOGGEDOUT, LOGGEDIN, GAMEPLAY, QUIT
    }
}
