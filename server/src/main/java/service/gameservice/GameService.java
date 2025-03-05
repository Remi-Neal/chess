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
        if(Authenticator.validAuth(authDAO, authData.authToken())) {
            return ListGamesService.listGames(gameDAO);
        }
        return null;
    }
    public GameDataType createGame(AuthtokenDataType authData, String gameName){
        if(Authenticator.validAuth(authDAO, authData.authToken())) {
            GameDataType newGame = CreateGameService.createGame(gameDAO, gameName);
            CreateGameService.addGame(gameDAO, newGame);
            CreateGameService.addNewBoard(gameDAO, newGame.gameId());
            return newGame;
        }
        return null;
    }
    public void joinGame(String authToken, int gameId, String color) throws IllegalArgumentException{
        AuthtokenDataType authData = authDAO.getAuth(authToken);
        if(authData != null) {
            if(color.equalsIgnoreCase("black")){
                JoinGameService.blackJoin(gameDAO, authData.username(), gameId);
            } else if (color.equalsIgnoreCase("white")) {
                JoinGameService.whiteJoin(gameDAO, authData.username(), gameId);
            } else {
                throw new IllegalArgumentException("Must choose 'black' or 'white' piece color.");
            }
        }
    }
}
