package service.gameservice;

import chess.ChessGame;
import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.GameDataType;
import service.ServiceInterface;
import service.userservice.methods.Authenticator;

import java.util.List;

public class GameService implements ServiceInterface{
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public GameService(){
        this.gameDAO = ServiceInterface.daoRecord.getGameDAO();
        this.authDAO = ServiceInterface.daoRecord.getAuthDAO();
    }

    public List<GameDataType> listGames(AuthtokenDataType authData){
        if(Authenticator.validAuth(authDAO, authData.getAuthToken())) {
            return ListGamesService.listGames(gameDAO);
        }
        return null;
    }
    public GameDataType createGame(AuthtokenDataType authData, String gameName){
        if(Authenticator.validAuth(authDAO, authData.getAuthToken())) {
            GameDataType newGame = CreateGameService.createGame(gameDAO, gameName);
            newGame.setGame(new ChessGame());
            CreateGameService.addGame(gameDAO, newGame);
            return newGame;
        }
        return null;
    }
    public void joinGame(AuthtokenDataType authData, int gameId, String userName, String color) throws IllegalArgumentException{
        if(Authenticator.validAuth(authDAO, authData.getAuthToken())) {
            if(color.equalsIgnoreCase("black")){
                JoinGameService.blackJoin(gameDAO, userName, gameId);
            } else if (color.equalsIgnoreCase("white")) {
                JoinGameService.whiteJoin(gameDAO, userName, gameId);
            } else {
                throw new IllegalArgumentException("Must choose 'black' or 'white' piece color.");
            }
        }
    }
}
