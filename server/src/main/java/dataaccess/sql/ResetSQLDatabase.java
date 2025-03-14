package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.ResetDAO;

import java.sql.SQLException;

public class ResetSQLDatabase implements ResetDAO {
    final String userTableName;
    final String gameTableName;
    final String authTableName;

    public ResetSQLDatabase(String userTable, String gameTable, String authTable){
        userTableName = userTable;
        gameTableName = gameTable;
        authTableName = authTable;
    }

    @Override
    public void deleteUsers() throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "TRUNCATE %s".formatted(userTableName)
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
                    "TRUNCATE %s".formatted(authTableName)
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
                    "TRUNCATE %s".formatted(gameTableName)
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
}
