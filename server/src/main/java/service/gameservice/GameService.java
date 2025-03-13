package service.gameservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import datatypes.AuthtokenDataType;
import datatypes.GameDataType;
import service.DAORecord;
import service.exceptions.BadRequest;
import service.exceptions.Unauthorized;
import service.userservice.methods.Authenticator;

import java.util.List;

public class GameService{
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public GameService(DAORecord daoRecord){
        this.gameDAO = daoRecord.getGameDAO();
        this.authDAO = daoRecord.getAuthDAO();
    }

    public List<GameDataType> listGames(String authToken) throws DataAccessException {
        if(!Authenticator.validAuth(authDAO, authToken)){ throw new Unauthorized(); }
        return ListGamesService.listGames(gameDAO);
    }
    public GameDataType createGame(String authToken, String gameName) throws DataAccessException {
        if(!Authenticator.validAuth(authDAO, authToken)){ throw new Unauthorized(); }
        GameDataType newGame = CreateGameService.createGame(gameName);
        CreateGameService.addGame(gameDAO, newGame);
        return newGame;
    }
    public void joinGame(
            String authToken, int gameId, String color) throws IllegalArgumentException, DataAccessException {
        AuthtokenDataType authData = authDAO.getAuth(authToken);
        if(!Authenticator.validAuth(authDAO, authToken)){ throw new Unauthorized(); }
        if(color == null){ throw new BadRequest(); }
        if(color.equalsIgnoreCase("black")){
            JoinGameService.blackJoin(gameDAO, authData.username(), gameId);
        } else if (color.equalsIgnoreCase("white")) {
            JoinGameService.whiteJoin(gameDAO, authData.username(), gameId);
        } else {
            throw new BadRequest();
        }

    }
}
