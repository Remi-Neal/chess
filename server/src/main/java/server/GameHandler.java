package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import datatypes.GameDataType;
import server.models.GameModel;
import service.DAORecord;
import service.exceptions.Unauthorized;
import service.gameservice.GameService;
import spark.Request;

import java.util.List;
import java.util.Map;

public class GameHandler {
    private final GameService gameService;
    public GameHandler(DAORecord daoRecord){ this.gameService = new GameService(daoRecord); }
    public Object gameList(Request req) throws DataAccessException {
        String authToken = req.headers("authorization");
        List<GameDataType> gameList = gameService.listGames(authToken);
        return new Gson().toJson(Map.of("games", gameList));
    }
    public Object createGame(Request req) throws DataAccessException {
        String authToken = req.headers("authorization");
        var reqBody = new Gson().fromJson(req.body(), GameModel.class);
        String gameName = reqBody.gameName();
        GameDataType newGame = gameService.createGame(authToken, gameName);
        return new Gson().toJson(Map.of("gameID",newGame.gameID()));
    }
    public Object joinGame(Request req) throws DataAccessException {
        String authToken = req.headers("authorization");
        if(authToken == null){ throw new Unauthorized(); }
        var joinGameData = new Gson().fromJson(req.body(), GameModel.class);
        gameService.joinGame(authToken, joinGameData.gameID(), joinGameData.playerColor());
        return new Gson().toJson(Map.of());
    }
}
