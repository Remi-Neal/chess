package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.GameDAO;
import datatypes.GameDataType;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameSqlDAO implements GameDAO {
    private final String TABLE_NAME;
    public GameSqlDAO(String tableName){ TABLE_NAME = tableName; }

    private GameDataType createGameFromResultSet(ResultSet resultSet) throws DataAccessException {
        GameDataType gameData = null;
        try {
            if (resultSet.next()) {
                gameData = new GameDataType(
                        resultSet.getInt("gameId"),
                        resultSet.getString("whiteUserName"),
                        resultSet.getString("blackUserName"),
                        resultSet.getString("gameName")
                );
                gameData.setGameBoard(new Gson().fromJson(
                        resultSet.getString("chessGame"),
                        ChessGame.class));
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return gameData;
    }
    //TODO: implement gameList in GameSqlDAO
    @Override
    public List<GameDataType> gameList() throws DataAccessException {
        List<GameDataType> listOfGames = new ArrayList<>();
        try{
            var conn = DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM game"
            )){
                try(var resultSet = statement.executeQuery()){
                    while(resultSet.next()){
                        listOfGames.add(createGameFromResultSet(resultSet));
                    }
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return listOfGames;
    }

    @Override
    public void newGame(GameDataType gameData) throws DataAccessException {
        try{
            var conn = DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    """
                        INSERT INTO %s (gameId, whiteUserName, blackUserName, gameName, chessGame)
                        VALUES (?,?,?,?,?)
                        """.formatted(TABLE_NAME)
            )){
                statement.setInt(1,gameData.gameID());
                statement.setString(2, gameData.whiteUsername());
                statement.setString(3, gameData.blackUsername());
                statement.setString(4, gameData.gameName());
                String chessGameJSON = new Gson().toJson(gameData.getChessGame());
                statement.setString(5, chessGameJSON);
                statement.executeUpdate();
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    //TODO: implement findGame in GameSqlDAO
    @Override
    public GameDataType findGame(int gameId) throws DataAccessException {
        GameDataType gameData;
        try{
            var conn = DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM %s WHERE gameId = ?".formatted(TABLE_NAME)
            )){
                statement.setInt(1, gameId);
                try(var resultSet = statement.executeQuery()){
                    gameData = createGameFromResultSet(resultSet);
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return gameData;
    }

    //TODO: implement updateGameData in GameSqlDAO
    private void createUpdateWithConn(Connection conn, String column, String updatedData, int id) throws DataAccessException {
        try(var statement = conn.prepareStatement(
                "UPDATE %s SET ?=? WHERE id=?".formatted(TABLE_NAME)
        )){
            statement.setString(1,column);
            statement.setString(2,updatedData);
            statement.setInt(3,id);
            statement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void updateGameData(GameDataType oldData, GameDataType newData) throws DataAccessException {
        try{
            var conn = DatabaseManager.getConnection();
            if(!oldData.whiteUsername().equals(newData.whiteUsername())) {
                createUpdateWithConn(
                        conn,
                        "whiteUserName",
                        newData.whiteUsername(),
                        newData.gameID()
                );
            }
            if(!oldData.blackUsername().equals(newData.blackUsername())){
                createUpdateWithConn(
                        conn,
                        "blackUserName",
                        newData.blackUsername(),
                        newData.gameID()
                );
            }
            if(!oldData.gameName().equals(newData.gameName())){
                createUpdateWithConn(
                        conn,
                        "gameName",
                        newData.gameName(),
                        newData.gameID()
                );
            }
            if(!oldData.getChessGame().equals(newData.getChessGame())){
                createUpdateWithConn(
                        conn,
                        "chessGame",
                        new Gson().toJson(newData.getChessGame()),
                        newData.gameID()
                );
            }
        } catch(DataAccessException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
