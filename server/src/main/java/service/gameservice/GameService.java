package service.gameservice;

import chess.ChessBoard;
import chess.ChessMove;
import chess.InvalidMoveException;
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
import java.util.Objects;

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
    public ChessBoard getBoard(int gameID) throws DataAccessException {
        GameDataType gameData = gameDAO.findGame(gameID);
        return gameData.chessGame().getBoard();
    }
    public GameDataType getGame(int gameID) throws DataAccessException{
        return gameDAO.findGame(gameID);
    }
    public ChessBoard makeMove(int gameID, String authToken, ChessMove move) throws DataAccessException, InvalidMoveException {
        GameDataType gameData = gameDAO.findGame(gameID);
        String userName = authDAO.getAuth(authToken).username();
        if(!(gameData.blackUsername().equals(userName) || gameData.whiteUsername().equals(userName))){
            throw new BadRequest();
        }
        try{
            gameData.chessGame().makeMove(move);
        } catch (InvalidMoveException e) {
            throw new InvalidMoveException();
        }
        gameDAO.updateGameData(gameData, new GameDataType(
                gameID,
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                gameData.chessGame().copyChessGame(),
                gameData.active()
        ));
        return gameData.chessGame().getBoard();
    }

    public void removePlayer(int gameID, String userName) throws DataAccessException {
        GameDataType gameData = gameDAO.findGame(gameID);
        if(Objects.equals(gameData.blackUsername(), userName)){
            gameDAO.updateGameData(gameData, new GameDataType(
                    gameData.gameID(),
                    gameData.whiteUsername(),
                    null,
                    gameData.gameName(),
                    gameData.chessGame(),
                    true
            ));
        } else if(Objects.equals(gameData.whiteUsername(), userName)){
            gameDAO.updateGameData(gameData, new GameDataType(
                    gameData.gameID(),
                    null,
                    gameData.blackUsername(),
                    gameData.gameName(),
                    gameData.chessGame(),
                    true
            ));
        }
    }

    public void closeGame(int gameID) throws DataAccessException {
        GameDataType oldDate = gameDAO.findGame(gameID);
        gameDAO.updateGameData(oldDate, new GameDataType(
                oldDate.gameID(),
                oldDate.whiteUsername(),
                oldDate.blackUsername(),
                oldDate.gameName(),
                oldDate.chessGame(),
                false
        ));
    }

    public boolean validateGameID(int gameID) {
        try {
            gameDAO.findGame(gameID);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

}
