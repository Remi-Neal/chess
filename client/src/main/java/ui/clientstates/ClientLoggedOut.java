package ui.clientstates;
import ui.ClientMain;
import ui.exceptions.ResponseException;
import ui.requestrecords.LoginRequest;
import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;
import ui.EventLoop.EventState;
import ui.requestrecords.RegistrationRequest;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class ClientLoggedOut {
// TODO: take out any excessive System.out calls
    public static void loggedOutUI(){
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
                if(tryLoggingIn()) {
                    eventState  = EventState.LOGGEDIN;
                    System.out.println("Logging in!");
                } else {
                    System.out.println("Unable to log in...");
                }
                break;
            case "register":
                if(tryRegistering()){
                    eventState = EventState.LOGGEDIN;
                    System.out.println("Registered!");
                } else {
                    System.out.println("Unable to register");
                }
                break;
            default:
                System.out.println(SET_TEXT_COLOR_RED + "Unknown " + RESET_TEXT_COLOR + command);
                System.out.println("Please use one of the following options...");
                System.out.println(LOGGEDOUT_HELP_STRING);
        }
    }

    private static boolean tryLoggingIn(){
        System.out.println("Trying to Login");
        String username;
        String password;
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
        try{
            var reponse = ClientMain.serverFacade.callLogin(new LoginRequest(username, password));
            System.out.println(reponse.toString());
            ClientMain.userName = reponse.username();
            ClientMain.authToken = reponse.authToken();
            return true;
        } catch (ResponseException e) {
            System.out.println(exceptionHandler(e));
        }
        return false;
    }

    private static boolean tryRegistering(){
        System.out.println("Registering user");
        String username = "";
        String password = "";
        String email;
        String[] line = scanner.nextLine().split(" ");
        int numItems = line.length - 1;
        if(numItems == 3){
            username = line[1];
            password = line[2];
            email = line[3];
        } else if(numItems == 2 ) {
            System.out.print(SET_TEXT_COLOR_BLUE + "Email: " + RESET_TEXT_COLOR);
            email = scanner.next();
        } else if(numItems == 1){
            System.out.print(SET_TEXT_COLOR_BLUE + "Password: " + RESET_TEXT_COLOR);
            password = scanner.next();
            System.out.print(SET_TEXT_COLOR_BLUE + "Email: " + RESET_TEXT_COLOR);
            email = scanner.next();
        } else {
            System.out.print(SET_TEXT_COLOR_BLUE + "Username: " + RESET_TEXT_COLOR);
            username = scanner.next();
            System.out.print(SET_TEXT_COLOR_BLUE + "Password: " + RESET_TEXT_COLOR);
            password = scanner.next();
            System.out.print(SET_TEXT_COLOR_BLUE + "Email: " + RESET_TEXT_COLOR);
            email = scanner.next();
        }
        System.out.println("Username: " + username + " Password: " + password + " Email: " + email);
        try{
            var response = ClientMain.serverFacade.callRegistration(new RegistrationRequest(username,password,email));
            System.out.println(response.toString());
            ClientMain.userName = response.username();
            ClientMain.authToken = response.authToken();
            return true;
        } catch (ResponseException e) {
            System.out.println(exceptionHandler(e));
        }
        return false;
    }

    private static String exceptionHandler(ResponseException e){
        if(e.statusCode() == 401){
            return "Username already take";
        }
        System.out.println(e.statusCode() + " " + e);
        return "Unable to process request. Please try again later.";
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
