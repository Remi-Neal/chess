package ui;

import ui.ClientStates.ClientLoggedOut;

import static ui.EscapeSequences.*;

public class EventLoop {
    public EventLoop(){

    }
    public static void run(){
        EventState state = EventState.LOGGEDOUT;
        System.out.print("👸 WELCOME to Chess a la BYU CS 240. ");
        System.out.print("Type ");
        System.out.print(SET_TEXT_BLINKING + "HELP" + RESET_TEXT_BLINKING);
        System.out.print(" to begin! 🤴\n");
        boolean run_loop = true;
        while(run_loop){
            switch(state){
                case LOGGEDOUT -> {
                    ClientLoggedOut.loggedOutUI();
                    if(ClientLoggedOut.isLoggedIn){
                        state = EventState.LOGGEDIN;
                    }
                    if(ClientLoggedOut.selectedQuit){
                        state = EventState.QUIT;
                    }
                    break;
                }
                case LOGGEDIN -> {
                    System.out.print(SET_BG_COLOR_RED + "Logged In state not implemented");
                    state = EventState.LOGGEDOUT;
                    break;
                }
                case GAMEPLAY -> {
                    System.out.print(SET_BG_COLOR_RED + "Gameplay state not implemented");
                    state = EventState.LOGGEDOUT;
                    break;
                }
                case QUIT -> {
                    System.out.println("GOODBY! 👋");
                    run_loop = false;
                }
            }
        }
    }

    private enum EventState{
        LOGGEDOUT, LOGGEDIN, GAMEPLAY, QUIT
    }
}
