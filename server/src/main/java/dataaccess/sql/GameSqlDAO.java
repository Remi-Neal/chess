package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.GameDAO;
import datatypes.GameDataType;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameSqlDAO implements GameDAO {
    private final String tableName;
    public GameSqlDAO(String tableName){ this.tableName = tableName; }

    private GameDataType createGameFromResultSet(ResultSet resultSet) throws DataAccessException {
        GameDataType gameData;
        try {
            gameData = new GameDataType(
                    resultSet.getInt("gameId"),
                    resultSet.getString("whiteUserName"),
                    resultSet.getString("blackUserName"),
                    resultSet.getString("gameName"),
                    new Gson().fromJson(resultSet.getString("chessGame"), ChessGame.class),
                    resultSet.getBoolean("active")
            );
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return gameData;
    }

    @Override
    public List<GameDataType> gameList() throws DataAccessException {
        List<GameDataType> listOfGames = new ArrayList<>();
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT gameName, gameId, whiteUserName, blackUserName, chessGame FROM game"
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
                    "INSERT INTO %s ".formatted(tableName) +
                            "(gameId, whiteUserName, blackUserName, gameName, chessGame, active) " +
                            "VALUES (?,?,?,?,?,?)"
            )){
                statement.setInt(1,gameData.gameID());
                statement.setString(2, gameData.whiteUsername());
                statement.setString(3, gameData.blackUsername());
                statement.setString(4, gameData.gameName());
                String chessGameJSON = new Gson().toJson(gameData.chessGame());
                statement.setString(5, chessGameJSON);
                statement.setBoolean(6, gameData.active());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameDataType findGame(int gameId) throws DataAccessException {
        GameDataType gameData = null;
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM %s WHERE gameId = ?".formatted(tableName)
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

    private <T> void createUpdateWithConn(Connection conn, String column, T updatedData, int id) throws DataAccessException {
        try(var statement = conn.prepareStatement(
                "UPDATE %s SET %s=? WHERE gameId=?".formatted(tableName, column)
        )){
            if(updatedData instanceof Boolean){
                statement.setBoolean(1, (Boolean) updatedData);
            } else {
                statement.setString(1, (String) updatedData);
            }
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
            if(!Objects.equals(oldData.whiteUsername(), newData.whiteUsername())) {
                createUpdateWithConn(
                        conn,
                        "whiteUserName",
                        newData.whiteUsername(),
                        newData.gameID()
                );
            }
            if(!Objects.equals(oldData.blackUsername(), newData.blackUsername())){
                createUpdateWithConn(
                        conn,
                        "blackUserName",
                        newData.blackUsername(),
                        newData.gameID()
                );
            }
            if(oldData.gameName() != null && newData.gameName() != null) {
                if(!oldData.gameName().equals(newData.gameName())) {
                    createUpdateWithConn(
                            conn,
                            "gameName",
                            newData.gameName(),
                            newData.gameID()
                    );
                }
            }
            if(oldData.chessGame() == null) {
                if (newData.chessGame() == null) {
                    return;
                }
                createUpdateWithConn(
                        conn,
                        "chessGame",
                        new Gson().toJson(newData.chessGame()),
                        newData.gameID()
                );
            }
            if(!Objects.equals(oldData, newData)){
                createUpdateWithConn(
                        conn,
                        "chessGame",
                        new Gson().toJson(newData.chessGame()),
                        newData.gameID()
                );
            }
            if(!newData.active()){
                createUpdateWithConn(
                        conn,
                        "active",
                        false,
                        newData.gameID()
                );
            }
        } catch(DataAccessException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
