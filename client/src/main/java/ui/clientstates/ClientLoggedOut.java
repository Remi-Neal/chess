package ui.clientstates;
import ui.ClientMain;
import ui.EventLoop;
import ui.ServerFacade;
import ui.server_request_records.LoginRequest;
import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;
import ui.EventLoop.EventState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ClientLoggedOut {
    public static boolean isLoggedIn;

    public static void loggedOutUI(){
        isLoggedIn = false;
        System.out.print(SET_TEXT_COLOR_RED + "[LOGGED_OUT] >>> " + RESET_TEXT_COLOR);
        String command = scanner.next();
        switch(command.toLowerCase()) {
            case "help":
            case "h":
                System.out.println(LOGGEDOUT_HELP_STRING);
                break;
            case "quit":
            case "q":
                eventState = EventState.QUIT;
                break;
            case "login":
                if(tryLoggingIn(scanner)) {
                    eventState  = EventState.LOGGEDIN;
                    System.out.println("Logging in!");
                } else {
                    System.out.println("Unable to log in...");
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
        String username = "";
        String password = "";
        String[] line = scanner.nextLine().split(" ");

        System.out.println("Text read:" + Arrays.toString(line) + " length: " + line.length);
        if(line.length == 3){
            username = line[1];
            password = line[2];
        } else if(line.length == 2){
            username = line[1];
            System.out.print(SET_TEXT_COLOR_BLUE + "Password: " + RESET_TEXT_COLOR);
            password = scanner.next();
        } else {
            System.out.print(SET_TEXT_COLOR_BLUE + "Username: " + RESET_TEXT_COLOR);
            username = scanner.next();
            System.out.print(SET_TEXT_COLOR_BLUE + "Password: " + RESET_TEXT_COLOR);
            password = scanner.next();
        }
        System.out.println("Username = " + username);
        System.out.println("Password = " + password);
        // This stuff is bad plz fix quick
        try{
            var reponse = ClientMain.serverFacade.callLogin(new LoginRequest(username, password));
            System.out.println(reponse.toString());
            return true; // TODO: idk what to do with this response
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
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
