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
        String command = scanner.next();
        switch(command.toLowerCase()) {
            case "help":
            case "h":
                System.out.println(LOGGEDOUT_HELP_STRING);
                break;
            case "quit":
            case "q":
                selectedQuit = true;
                scanner.close();
                break;
            case "login":
                isLoggedIn = tryLoggingIn(scanner);
                if(isLoggedIn) {
                    scanner.close();
                }
                break;
            case "register":
                isLoggedIn = tryRegisterng(scanner);
            default:
                System.out.println(SET_TEXT_COLOR_RED + "Unknown " + RESET_TEXT_COLOR + command);
                System.out.println("Please use one of the following options...");
                System.out.println(LOGGEDOUT_HELP_STRING);
        }
    }

    private static boolean tryLoggingIn(Scanner scanner){
        System.out.println("Trying to Login");
        String[] logInData = scanner.nextLine().split(" ");
        switch(logInData.length){
            case 2:
                break;
            case 1:
                System.out.println("Missing <PASSWORD>");
                return false;
            default:
                System.out.println("Missing <USERNAME> <PASSWORD>");
                return false;
        }
        if(!scanner.hasNext()){

        }
        System.out.println("Username = " + logInData[0]);
        System.out.println("Password = " + logInData[1]);
        // TODO: add api call to login
        System.out.println("Login functionality is not implemented");
        return false;
    }

    private static boolean tryRegisterng(Scanner scanner){
        if(!scanner.hasNext()){
            System.out.println("Missing <USERNAME> <PASSWORD> <EMAIL>");
        }
        String userName = scanner.next();
        if(!scanner.hasNext()){
            System.out.println("Missing <PASSWORD> <EMAIL>");
        }
        String password = scanner.next();
        if(!scanner.hasNext()){
            System.out.println("Missing <EMAIL>");
        }
        String email = scanner.next();
        // TODO: add api call to register
        System.out.println("Username: " + userName + " Password: " + password + " Email: " + email);
        System.out.println("Registration functionality is not implemented");
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
