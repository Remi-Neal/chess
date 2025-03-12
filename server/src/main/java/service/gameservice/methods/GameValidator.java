package service.gameservice.methods;

import dataaccess.interfaces.GameDAO;

public class GameValidator {
    public static boolean validateGame(GameDAO gameDAO, int gameID){
        return gameDAO.findGame(gameID) != null;
    }
}
