package service.gameservice;

import chess.ChessGame;
import dataaccess.gamedata.GameDAO;
import memorydatabase.datatypes.GameDataType;


public class CreateGameService {
    static final int MAX_INT_SIZE = 2147483647;
    public static GameDataType createGame(String gameName){
        int gameID = (int) (Math.random() * MAX_INT_SIZE);
        GameDataType newGame = new GameDataType(gameID, null, null, gameName);
        addNewBoard(newGame);
        return newGame;
    }
    public static void addGame(GameDAO gameDAO, GameDataType game){
        gameDAO.newGame(game);
    }
    private static void addNewBoard(GameDataType game){
        game.setGameBoard(new ChessGame());
    }
}
