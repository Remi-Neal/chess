package service.gameservice.methods;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;

public class GameValidator {
    public static boolean validateGame(GameDAO gameDAO, int gameID) throws DataAccessException {
        return gameDAO.findGame(gameID) != null;
    }
}
