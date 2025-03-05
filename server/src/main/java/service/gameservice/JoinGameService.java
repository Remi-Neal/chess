package service.gameservice;

import dataaccess.gamedata.GameDAO;
import database.datatypes.GameDataType;

public class JoinGameService {
    public static void blackJoin(GameDAO gameDAO, String userName, int gameId){
        GameDataType game = gameDAO.findGame(gameId);
        GameDataType blackGame = game.copy();
        blackGame.setBlack(userName);
        gameDAO.updateGameData(game, blackGame);
        //TODO: Check if there is already a black player
    }
    public static void whiteJoin(GameDAO gameDAO, String userName, int gameId){
        GameDataType game = gameDAO.findGame(gameId);
        GameDataType whiteGame = game.copy();
        whiteGame.setBlack(userName);
        gameDAO.updateGameData(game, whiteGame);
        //TODO: Check if there is already a white player
    }
}
