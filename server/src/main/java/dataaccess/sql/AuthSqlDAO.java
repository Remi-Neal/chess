package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.AuthDAO;
import datatypes.AuthtokenDataType;

import java.sql.SQLException;

public class AuthSqlDAO implements AuthDAO {
    private final String tableName;
    public AuthSqlDAO(String tableName){
        this.tableName = tableName;
    }

    @Override
    public void createAuth(AuthtokenDataType auth) throws DataAccessException {
        try(
            var conn = SqlDAO.getConnection();
            var statement = conn.prepareStatement(
                    "INSERT INTO %s (name, auth) VALUES (?,?)".formatted(tableName)
            )){
            statement.setString(1, auth.username());
            statement.setString(2, auth.authToken());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void removeAuth(String token) throws DataAccessException {
        try(
            var conn = SqlDAO.getConnection();
            var statement = conn.prepareStatement(
                    "DELETE FROM %s WHERE auth = ?".formatted(tableName)
            )){
            statement.setString(1, token);
            statement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthtokenDataType getAuth(String token) throws DataAccessException {
        AuthtokenDataType authData = null;
        try(
            var conn = SqlDAO.getConnection();
            var statement = conn.prepareStatement(
                    "SELECT * FROM %s WHERE auth = ?".formatted(tableName)
            )){
            statement.setString(1, token);
            try(var resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    authData = new AuthtokenDataType(
                            resultSet.getString("auth"),
                            resultSet.getString("name")
                    );
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return authData;
    }
}
