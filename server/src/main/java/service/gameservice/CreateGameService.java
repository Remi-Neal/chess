package service.gameservice;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import datatypes.GameDataType;


public class CreateGameService {
    static final int MAX_INT_SIZE = 2147483647;
    public static GameDataType createGame(String gameName){
        int gameID = (int) (Math.random() * MAX_INT_SIZE);
        return new GameDataType(
                gameID,
                null,
                null,
                gameName,
                new ChessGame(),
                true);
    }
    public static void addGame(GameDAO gameDAO, GameDataType game) throws DataAccessException {
        gameDAO.newGame(game);
    }
}
