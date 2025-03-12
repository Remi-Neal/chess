package service.gameservice;

import dataaccess.gamedata.GameDAO;
import memorydatabase.datatypes.GameDataType;

import java.util.List;

public class ListGamesService {
    public static List<GameDataType> listGames(GameDAO gameDAO){
        return gameDAO.gameList();
    }
}
