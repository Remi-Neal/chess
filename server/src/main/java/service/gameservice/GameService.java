package service.gameservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;
import service.ServiceInterface;

import java.util.List;

public class GameService implements ServiceInterface {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public GameService(){
        this.gameDAO = ServiceInterface.daoRecord.getGameDAO();
        this.authDAO = ServiceInterface.daoRecord.getAuthDAO();
    }

    public List<GameDataType> listGames(AuthtokenDataType authData){
        return ListGamesService.listGames(gameDAO, authDAO, authData);
    }
    public GameDataType createGame(AuthtokenDataType authData, String gameName){
        return CreateGameService.createGame(gameDAO, authDAO, authData, gameName);
    }
}
