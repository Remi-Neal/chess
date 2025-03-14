package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.SqlDAO;
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
            gameData = new GameDataType(
                    resultSet.getInt("gameId"),
                    resultSet.getString("whiteUserName"),
                    resultSet.getString("blackUserName"),
                    resultSet.getString("gameName")
            );
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
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT gameName, gameId, whiteUserName, blackUserName FROM game"
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
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "INSERT INTO %s ".formatted(TABLE_NAME) +
                            "(gameId, whiteUserName, blackUserName, gameName, chessGame) VALUES (?,?,?,?,?)"
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
        GameDataType gameData = null;
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM %s WHERE gameId = ?".formatted(TABLE_NAME)
            )){
                statement.setInt(1, gameId);
                try(var resultSet = statement.executeQuery()){
                    if(resultSet.next()) {
                        gameData = createGameFromResultSet(resultSet);
                    }
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
                "UPDATE %s SET %s=? WHERE gameId=?".formatted(TABLE_NAME, column)
        )){
            statement.setString(1,updatedData);
            statement.setInt(2,id);
            statement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void updateGameData(GameDataType oldData, GameDataType newData) throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            if(oldData.whiteUsername() == null && newData.whiteUsername() != null) {
                createUpdateWithConn(
                        conn,
                        "whiteUserName",
                        newData.whiteUsername(),
                        newData.gameID()
                );
            }
            if(oldData.blackUsername() == null && newData.blackUsername() != null){
                createUpdateWithConn(
                        conn,
                        "blackUserName",
                        newData.blackUsername(),
                        newData.gameID()
                );
            }
            if(oldData.gameName() == null && newData.gameName() != null){
                createUpdateWithConn(
                        conn,
                        "gameName",
                        newData.gameName(),
                        newData.gameID()
                );
            }
            if(oldData.getChessGame() == null) {
                if (newData.getChessGame() == null) {
                    return;
                }
                if (oldData.getChessGame().equals(newData.getChessGame())) { return; }
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
