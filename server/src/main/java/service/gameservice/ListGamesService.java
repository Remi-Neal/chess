package service.gameservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;

import java.util.List;

public class ListGamesService {
    public static List<GameDataType> listGames(GameDAO gameDAO){
        return gameDAO.gameList();
    }
}
