package ui.clientstates;
import ui.ClientMain;
import ui.EventLoop;
import ui.exceptions.ResponseException;
import ui.requestrecords.CreateGameRequest;
import ui.requestrecords.JoinRequest;
import ui.responcerecord.GameDataResponse;

import java.util.ArrayList;
import java.util.List;

import static ui.EscapeSequences.*;

import static ui.EventLoop.scanner;
import static ui.EventLoop.eventState;

public class ClientLoggedIn {
    // TODO: take out any excessive System.out calls
    public static void loggedinUI() {
        System.out.print(SET_TEXT_COLOR_RED +"[LOGGED_IN] >>> "+RESET_TEXT_COLOR);
        String command = scanner.next();
        switch(command.toLowerCase()){
            case "create":
                System.out.println("Called Created");
                tryCreatingGame();
                break;
            case "list":
                System.out.println("Called list");
                tryListGames();
                break;
            case "join":
                System.out.println("Called join");
                tryJoiningGame();
                break;
            case "observe":
                System.out.println("Called observe");
                tryObserveGame();
                break;
            case "logout":
                System.out.println("Called logout");
                tryLoggingOut();
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
                System.out.println(LOGGEDIN_HELP_STRING);
        }
    }

    private static void tryLoggingOut(){
        System.out.println("AuthToken: " + ClientMain.authToken);
        if(ClientMain.authToken != null) {
            try {
                ClientMain.serverFacade.callLogout(ClientMain.authToken);
                System.out.println("Loggin out...");
                ClientMain.authToken = null;
                eventState = EventLoop.EventState.LOGGEDOUT;
            } catch (Exception e) {
                System.out.println("An error occurred, please try again");
            }
        }
    }

    private static void tryCreatingGame(){
        String[] line = scanner.nextLine().split(" ");
        String gameName;
        if(line.length == 1){
            System.out.print(SET_TEXT_COLOR_BLUE + "Game Name: " + RESET_TEXT_COLOR);
            gameName = scanner.next();
        } else {
            gameName = line[1];
        }
        if(ClientMain.authToken != null){
            try{
                var response = ClientMain.serverFacade.callCreateGame(ClientMain.authToken, new CreateGameRequest(gameName));
                System.out.println("Game Created!");
                System.out.println("GameID: " + response.gameID());
            } catch (ResponseException e) {
                System.out.println("Error num: " + e.statusCode() + " Message: " + e);
                System.out.println("An error occurred, please try again");
            }
        }
    }

    private static void getGameList(){
        if(ClientMain.authToken != null){
            try{
                var response = ClientMain.serverFacade.callListGames(ClientMain.authToken);
                ClientMain.currGameList = new ArrayList<>(List.of(response.games()));
            } catch (ResponseException e){
                System.out.println("Error num: " + e.statusCode() + " Message: " + e);
                System.out.println("An error occurred, please try again");
            }
        }
    }

    private static void tryListGames(){
        System.out.println("AuthToken: " + ClientMain.authToken);
        getGameList();
        outputGameList();
    }

    private static void tryJoiningGame(){
        System.out.println("AuthToken: " + ClientMain.authToken);
        String[] line = scanner.nextLine().split(" ");
        String color;
        int gameID;
        if(line.length == 2){
            System.out.print(SET_TEXT_COLOR_BLUE + "Color: " + RESET_TEXT_COLOR);
            color = scanner.next();
            gameID = Integer.parseInt(line[1]);
        } else if(line.length == 1){
            System.out.print(SET_TEXT_COLOR_BLUE + "Color: " + RESET_TEXT_COLOR);
            gameID = Integer.parseInt(scanner.next());
            System.out.print(SET_TEXT_COLOR_BLUE + "Color: " + RESET_TEXT_COLOR);
            color = scanner.next();
        } else{
            gameID = Integer.parseInt(line[1]);
            color = line[2];
        }
        if(findGame(gameID) == null){
            System.out.println("Unknown game ID");
            return;
        }
        if(ClientMain.authToken != null){
           if(findGame(gameID).whiteUsername() != null){
               if(findGame(gameID).whiteUsername().equals(ClientMain.userName)){
                   eventState = EventLoop.EventState.GAMEPLAY;
                   ClientMain.activeGame = gameID;
                   return;
               }
           }
           if(findGame(gameID).blackUsername() != null){
               if(findGame(gameID).blackUsername().equals(ClientMain.userName)){
                   eventState = EventLoop.EventState.GAMEPLAY;
                   ClientMain.activeGame = gameID;
                   return;
               }
           }
            try {
                ClientMain.serverFacade.callJoinGame(ClientMain.authToken, new JoinRequest(color, gameID));
                ClientMain.activeGame = gameID;
                eventState = EventLoop.EventState.GAMEPLAY;
            } catch (ResponseException e) {
                System.out.println("Unable to join game. Please try again");
            }
        }
    }

    private static GameDataResponse findGame(int gameID){
        if(ClientMain.currGameList == null){
            getGameList();
        }
        for(GameDataResponse game : ClientMain.currGameList){
            if(game.gameID() == gameID){
                return game;
            }
        }
        return null;
    }

    private static void tryObserveGame(){
        String[] line = scanner.nextLine().split(" ");
        int gameID;
        if(line.length == 1){
            System.out.print(SET_TEXT_COLOR_BLUE + "Color: " + RESET_TEXT_COLOR);
            gameID = Integer.parseInt(scanner.next());
        } else {
            gameID = Integer.parseInt(line[1]);
        }
        ClientMain.activeGame = gameID;
        eventState = EventLoop.EventState.GAMEPLAY;
    }

    private static void outputGameList(){
        System.out.println(SET_TEXT_COLOR_GREEN + "Games:" + RESET_TEXT_COLOR);
        String delim = "-------";
        for(GameDataResponse game : ClientMain.currGameList){
            System.out.println(delim);
            System.out.println(SET_TEXT_COLOR_BLUE + "\tGame Name: " + RESET_TEXT_COLOR + game.gameName());
            System.out.print(
                    SET_TEXT_COLOR_BLUE + "\tWhite Player" + WHITE_KING + ": " + RESET_TEXT_COLOR);
            if(game.whiteUsername() != null){
                System.out.println(game.whiteUsername());
            } else{
                System.out.println();
            }
            System.out.print(
                    SET_TEXT_COLOR_BLUE + "\tBlack Player" + BLACK_KING + ": " + RESET_TEXT_COLOR);
            if(game.blackUsername() != null){
                System.out.println(game.blackUsername());
            } else {
                System.out.println();
            }
            System.out.println(SET_TEXT_COLOR_BLUE + "\tGame ID: " + RESET_TEXT_COLOR + game.gameID());
        }
        System.out.println(delim);
    }

   static final String LOGGEDIN_HELP_STRING =
           SET_TEXT_COLOR_GREEN + "create <NAME>" + RESET_TEXT_COLOR + " - to create a game\n" +
           SET_TEXT_COLOR_GREEN + "list" + RESET_TEXT_COLOR + " - to list all games\n" +
           SET_TEXT_COLOR_GREEN + "join <ID> [WHITE | BLACK]" + RESET_TEXT_COLOR +
                   " - to join game with game ID and select your color\n" +
           SET_TEXT_COLOR_GREEN + "observe <ID>" + RESET_TEXT_COLOR + " - to observer game with game ID\n" +
           SET_TEXT_COLOR_GREEN + "logout" + RESET_TEXT_COLOR + " - to loggout of chess\n" +
           SET_TEXT_COLOR_GREEN + "quit | q" + RESET_TEXT_COLOR + " - to quit playing chess\n" +
           SET_TEXT_COLOR_GREEN + "help | h" + RESET_TEXT_COLOR + " - list possible commands";
}
