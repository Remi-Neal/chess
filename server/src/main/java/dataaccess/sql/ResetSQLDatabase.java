package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.ResetDAO;

import java.sql.SQLException;

public class ResetSQLDatabase implements ResetDAO {
    final String USER_TABLE_NAME;
    final String GAME_TABLE_NAME;
    final String AUTH_TABLE_NAME;

    public ResetSQLDatabase(String userTable, String gameTable, String authTable){
        USER_TABLE_NAME = userTable;
        GAME_TABLE_NAME = gameTable;
        AUTH_TABLE_NAME = authTable;
    }

    @Override
    public void deleteUsers() throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "TRUNCATE %s".formatted(USER_TABLE_NAME)
            )){
                statement.executeUpdate();
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth() throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "TRUNCATE %s".formatted(AUTH_TABLE_NAME)
            )){
                statement.executeUpdate();
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteGames() throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "TRUNCATE %s".formatted(GAME_TABLE_NAME)
            )){
                statement.executeUpdate();
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void run() throws DataAccessException {
        deleteUsers();
        deleteGames();
        deleteAuth();
    }
    //TODO: add reset to SQL database

}
