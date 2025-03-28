package ui.clientstates;
import ui.ClientMain;
import ui.EventLoop;

import static ui.EscapeSequences.*;

import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;

public class ClientLoggedIn {
    public static void loggedinUI() {
        System.out.print(SET_TEXT_COLOR_RED +"[LOGGED_IN] >>> "+RESET_TEXT_COLOR);
        String command = scanner.next();
        switch(command.toLowerCase()){
            case "create":
            case "list":
            case "join":
            case "observe":
                break;
            case "logout":
                //TODO: create api call to logout from the server
                if(tryLoggingOut()) {
                    eventState  = EventLoop.EventState.LOGGEDOUT;
                    System.out.println("Logging out!");
                } else {
                    System.out.println("Unable to log out...");
                }
                break;
            case "quit":
            case "q":
                eventState = EventLoop.EventState.QUIT;
                break;
            case "help":
            case "h":
                System.out.println(LOGGEDIN_HELP_STRING);
                break;
            default:
                System.out.println(SET_TEXT_COLOR_RED + "Unknown " + RESET_TEXT_COLOR + command);
                System.out.println("Please use one of the following options...");
                System.out.println(LOGGEDIN_HELP_STRING);;
        }
    }

    private static boolean tryLoggingOut(){
        if(ClientMain.authToken != null) {
            try {
                ClientMain.serverFacade.callLogout(ClientMain.authToken);
                System.out.println("Loggin out...");
                ClientMain.authToken = null;
                return true;
            } catch (Exception e) {
                System.out.println("An error occurred, please try again");
                return false;
            }
        }
        return false;
    }

   static final String LOGGEDIN_HELP_STRING =
           SET_TEXT_COLOR_GREEN + "create <NAME>" + RESET_TEXT_COLOR + " - to create a game\n" +
           SET_TEXT_COLOR_GREEN + "list" + RESET_TEXT_COLOR + " - to list all games\n" +
           SET_TEXT_COLOR_GREEN + "join <ID> [WHITE | BLACK]" + RESET_TEXT_COLOR +
                   " - to join game with game ID and select your color\n" +
           SET_TEXT_COLOR_GREEN + "observe <ID>" + RESET_TEXT_COLOR + " - to observer game with game ID\n" +
           SET_TEXT_COLOR_GREEN + "loggout" + RESET_TEXT_COLOR + " - to loggout of chess\n" +
           SET_TEXT_COLOR_GREEN + "quit | q" + RESET_TEXT_COLOR + " - to quit playing chess\n" +
           SET_TEXT_COLOR_GREEN + "help | h" + RESET_TEXT_COLOR + " - list possible commands";
}
