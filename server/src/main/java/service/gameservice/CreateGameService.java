package service.gameservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;

import java.util.UUID;

public class CreateGameService {
    public static GameDataType createGame(GameDAO gameDAO, String gameName){
        GameDataType newGame = new GameDataType(UUID.randomUUID().clockSequence(), gameName);
        gameDAO.newGame(newGame);
        return newGame;
    }
    public static void addGame(GameDAO gameDAO, GameDataType game){
        gameDAO.newGame(game);
    }
}
