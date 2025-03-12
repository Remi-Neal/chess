package service.gameservice;

import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import memorydatabase.datatypes.AuthtokenDataType;
import memorydatabase.datatypes.GameDataType;
import service.exceptions.BadRequest;
import service.exceptions.Unauthorized;
import service.ServiceInterface;
import service.userservice.methods.Authenticator;

import java.util.List;

public class GameService implements ServiceInterface{
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public GameService(){
        this.gameDAO = ServiceInterface.DAO_RECORD.getGameDAO();
        this.authDAO = ServiceInterface.DAO_RECORD.getAuthDAO();
    }

    public List<GameDataType> listGames(String authToken){
        if(!Authenticator.validAuth(authDAO, authToken)){ throw new Unauthorized(); }
        return ListGamesService.listGames(gameDAO);
    }
    public GameDataType createGame(String authToken, String gameName){
        if(!Authenticator.validAuth(authDAO, authToken)){ throw new Unauthorized(); }
        GameDataType newGame = CreateGameService.createGame(gameName);
        CreateGameService.addGame(gameDAO, newGame);
        return newGame;
    }
    public void joinGame(String authToken, int gameId, String color) throws IllegalArgumentException{
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
