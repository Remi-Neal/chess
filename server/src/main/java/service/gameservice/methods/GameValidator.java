package service.gameservice.methods;

import dataaccess.gamedata.GameDAO;

public class GameValidator {
    public static boolean validateGame(GameDAO gameDAO, int gameID){
        return gameDAO.findGame(gameID) != null;
    }
}
