package service.gameservice;

import dataaccess.gamedata.GameDAO;
import database.datatypes.GameDataType;
import service.exceptions.BadRequest;
import service.exceptions.Forbidden;
import service.gameservice.methods.GameValidator;

public class JoinGameService {
    public static void blackJoin(GameDAO gameDAO, String userName, int gameId){
        if(!GameValidator.validateGame(gameDAO, gameId)){ throw new BadRequest(); }
        GameDataType game = gameDAO.findGame(gameId);
        if(game.blackUsername() != null){ throw new Forbidden(); }
        GameDataType blackGame = new GameDataType(game.gameID(), game.whiteUsername(), userName, game.gameName());
        blackGame.setGameBoard(game.getChessGame());
        gameDAO.updateGameData(game, blackGame);
    }
    public static void whiteJoin(GameDAO gameDAO, String userName, int gameId){
        if(!GameValidator.validateGame(gameDAO, gameId)){ throw new BadRequest(); }
        GameDataType game = gameDAO.findGame(gameId);
        if(game.whiteUsername() != null){ throw new Forbidden(); }
        GameDataType whiteGame = new GameDataType(game.gameID(), userName, game.blackUsername(), game.gameName());
        whiteGame.setGameBoard(game.getChessGame());
        gameDAO.updateGameData(game, whiteGame);
    }


}
