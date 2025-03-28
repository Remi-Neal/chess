package ui;

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
        boolean run_loop = true;
        while(run_loop){
            switch(eventState){
                case LOGGEDOUT -> {
                    ClientLoggedOut.loggedOutUI();
                }
                case LOGGEDIN -> {
                    System.out.println(SET_BG_COLOR_RED + "Logged In state not implemented");
                    eventState = EventState.QUIT;
                }
                case GAMEPLAY -> {
                    System.out.println(SET_BG_COLOR_RED + "Gameplay state not implemented");
                    eventState = EventState.LOGGEDOUT;
                }
                case QUIT -> {
                    System.out.println("GOODBY! 👋");
                    run_loop = false;
                }
            }
        }
    }

    public enum EventState{
        LOGGEDOUT, LOGGEDIN, GAMEPLAY, QUIT
    }
}
