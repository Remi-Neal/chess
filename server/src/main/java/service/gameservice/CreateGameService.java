package service.gameservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;
import service.userservice.methods.Authenticator;

import java.util.UUID;

public class CreateGameService {
    public static GameDataType createGame(GameDAO gameDAO, AuthDAO authDAO, AuthtokenDataType authData, String gameName){
        if(Authenticator.validAuth(authDAO,authData.getAuthToken())){
            GameDataType newGame = new GameDataType(UUID.randomUUID().clockSequence(), gameName);
            gameDAO.newGame(newGame);
            return newGame;
        }
        return null;
    }
}
