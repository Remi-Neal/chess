package server;

import com.google.gson.Gson;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;
import jdk.jshell.spi.ExecutionControl;
import server.models.GameModel;
import service.gameservice.GameService;
import spark.Request;
import spark.Response;

import java.util.List;

public class GameHandler {
    final static GameService gameService = new GameService();
    //TODO: Add methods to handle major User functions
    // Methods receive GSON data, unpack it, then format it to call service endpoints
    // Finally methods will turn returned data back into GSON and send it back to the server

    //TODO: Add error handling to methods to return the proper err messages

    public static Object gameList(Request req, Response res){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                "");
        List<GameDataType> gameList = gameService.listGames(auth);
        return new Gson().toJson(gameList);
    }
    public static Object createGame(Request req, Response res){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                "");
        var reqBody = new Gson().fromJson(req.body(), GameModel.class);
        String gameName = reqBody.gameName();
        return gameService.createGame(auth, gameName);
    }
    public static Object joinGame(Request req, Response res) {
        //TODO: Implemente joinGame
        var joinGameData = new Gson().fromJson(req.body(), GameModel.class);
        String authToken = req.headers("authorization");
        gameService.joinGame(authToken, joinGameData.gameID(), joinGameData.playerColor());
        return new Gson().toJson(null);
    }
}
