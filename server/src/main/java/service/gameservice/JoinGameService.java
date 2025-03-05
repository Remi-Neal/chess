package service.gameservice;

import dataaccess.gamedata.GameDAO;
import database.datatypes.GameDataType;
import service.ServiceExceptions.Unauthorized;

public class JoinGameService {
    public static void blackJoin(GameDAO gameDAO, String userName, int gameId){
        GameDataType game = gameDAO.findGame(gameId);
        if(!game.blackUsername().isEmpty()) throw new Unauthorized();
        GameDataType blackGame = new GameDataType(game.gameId(), game.whiteUsername(), userName, game.gameName());
        blackGame.setGameBoard(game.getChessGame());
        gameDAO.updateGameData(game, blackGame);
        //TODO: Check if there is already a black player
    }
    public static void whiteJoin(GameDAO gameDAO, String userName, int gameId){
        GameDataType game = gameDAO.findGame(gameId);
        if(!game.whiteUsername().isEmpty()) throw new Unauthorized();
        GameDataType whiteGame = new GameDataType(game.gameId(), userName, game.blackUsername(), game.gameName());
        whiteGame.setGameBoard(game.getChessGame());
        gameDAO.updateGameData(game, whiteGame);
        //TODO: Check if there is already a white player
    }
}
