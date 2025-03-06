package service.gameservice;

import chess.ChessGame;
import dataaccess.gamedata.GameDAO;
import database.datatypes.GameDataType;


public class CreateGameService {
    public static GameDataType createGame(String gameName){
        int gameID = (int) (Math.random() * 2147483647);
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
