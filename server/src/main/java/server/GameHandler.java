package server;

import com.google.gson.Gson;
import datatypes.GameDataType;
import server.models.GameModel;
import service.exceptions.Unauthorized;
import service.gameservice.GameService;
import spark.Request;

import java.util.List;
import java.util.Map;

public class GameHandler {
    final static GameService GAME_SERVICE = new GameService();
    public static Object gameList(Request req){
        String authToken = req.headers("authorization");
        List<GameDataType> gameList = GAME_SERVICE.listGames(authToken);
        return new Gson().toJson(Map.of("games", gameList));
    }
    public static Object createGame(Request req){
        String authToken = req.headers("authorization");
        var reqBody = new Gson().fromJson(req.body(), GameModel.class);
        String gameName = reqBody.gameName();
        GameDataType newGame = GAME_SERVICE.createGame(authToken, gameName);
        return new Gson().toJson(Map.of("gameID",newGame.gameID()));
    }
    public static Object joinGame(Request req) {
        String authToken = req.headers("authorization");
        if(authToken == null){ throw new Unauthorized(); }
        var joinGameData = new Gson().fromJson(req.body(), GameModel.class);
        GAME_SERVICE.joinGame(authToken, joinGameData.gameID(), joinGameData.playerColor());
        return new Gson().toJson(Map.of());
    }
}
