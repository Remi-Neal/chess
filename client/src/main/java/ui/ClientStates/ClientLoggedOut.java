package ui.ClientStates;
import java.util.Locale;
import java.util.Scanner;

import static java.awt.SystemColor.text;
import static ui.EscapeSequences.*;

public class ClientLoggedOut {
    public static boolean isLoggedIn;
    public static boolean selectedQuit;

    public static void loggedOutUI(){
        isLoggedIn = false;
        selectedQuit = false;
        System.out.print(SET_TEXT_COLOR_RED + "[LOGGED_OUT] >>> " + RESET_TEXT_COLOR);
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext()) {
            String command = scanner.next();
            switch(command.toLowerCase()) {
                case "help":
                case "h":
                    System.out.println(LOGGEDOUT_HELP_STRING);
                    break;
                case "quit":
                case "q":
                    selectedQuit = true;
                    break;
                case "login":
                    break;
                default:
                    System.out.println(SET_TEXT_COLOR_RED + "Unknown " + RESET_TEXT_COLOR + command);
                    System.out.println("Please use one of the following options...");
                    System.out.println(LOGGEDOUT_HELP_STRING);
            }
        }
        scanner.close();
    }

    private boolean tryLoggingIn(String userName, String password, String email){
        return false;
    }

    static final String LOGGEDOUT_HELP_STRING =
            SET_TEXT_COLOR_GREEN +
            "register <USERNAME> <PASSWORD> <EMAIL>" + RESET_TEXT_COLOR +  " - to create an account\n" +
            SET_TEXT_COLOR_GREEN +
            "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR + " - to play chess\n" +
            SET_TEXT_COLOR_GREEN +
            "quit | q" + RESET_TEXT_COLOR + " - to quit playing chess\n" +
            SET_TEXT_COLOR_GREEN +
            "help | h" + RESET_TEXT_COLOR + " - list possible commands";
}
