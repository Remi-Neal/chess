package service.gameservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import datatypes.GameDataType;

import java.util.List;

public class ListGamesService {
    public static List<GameDataType> listGames(GameDAO gameDAO) throws DataAccessException {
        return gameDAO.gameList();
    }
}
