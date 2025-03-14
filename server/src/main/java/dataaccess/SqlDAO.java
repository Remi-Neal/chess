package dataaccess;

import dataaccess.interfaces.*;
import dataaccess.sql.AuthSqlDAO;
import dataaccess.sql.GameSqlDAO;
import dataaccess.sql.ResetSQLDatabase;
import dataaccess.sql.UserSqlDAO;
import datatypes.GameDataType;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlDAO implements DAO {
    public static final String USER_TABLE_NAME = "user";
    public static final String GAME_TABLE_NAME = "game";
    public static final String AUTH_TABLE_NAME = "auth";

    public SqlDAO() {
        try {
            DatabaseManager.createDatabase();
            createTables();
        } catch(DataAccessException e){
           throw new RuntimeException(e.getMessage());
        }
    }

    public static Connection getConnection() throws DataAccessException {
        return DatabaseManager.getConnection();
    }

    static void createTables() throws DataAccessException {
        try {
            var conn = getConnection();
            try(var statement = conn.prepareStatement((
                    "CREATE TABLE IF NOT EXISTS %s ".formatted(USER_TABLE_NAME) +
                            "(name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(20));"
            ))){
                statement.executeUpdate();
            }
            try(var statement = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS %s ".formatted(AUTH_TABLE_NAME) +
                            "(name VARCHAR(255) NOT NULL, auth VARCHAR(36) NOT NULL);"
            )){
                statement.executeUpdate();
            }
            try(var statement = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS %s ".formatted(GAME_TABLE_NAME) +
                            "(gameId INT NOT NULL, whiteUserName VARCHAR(255), " +
                            "blackUserName VARCHAR(225), gameName VARCHAR(225) NOT NULL, chessGame text NOT NULL);"
            )){
                statement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserDAO makeUserDAO() {
        return new UserSqlDAO(USER_TABLE_NAME); // Hard coded but could be parameterized
    }

    @Override
    public GameDAO makeGameDAO() {
        return new GameSqlDAO(GAME_TABLE_NAME);
    }

    @Override
    public AuthDAO makeAuthDAO() { return new AuthSqlDAO(AUTH_TABLE_NAME); }

    @Override
    public ResetDAO makeResetDAO() { return new ResetSQLDatabase(USER_TABLE_NAME, GAME_TABLE_NAME, AUTH_TABLE_NAME); }
}
