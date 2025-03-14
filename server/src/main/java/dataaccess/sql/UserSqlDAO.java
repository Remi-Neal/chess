package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;
import java.sql.SQLException;

public class UserSqlDAO implements UserDAO {
    private final String tableName;
    public UserSqlDAO(String tableName){ this.tableName = tableName; }

    @Override
    public UserDataType getUser(String name) throws DataAccessException {
        UserDataType user = null; try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM %s WHERE name = ?".formatted(tableName))){
                statement.setString(1, name);
                try(var resultSet = statement.executeQuery()){
                    if(resultSet.next()) {
                        user = new UserDataType(
                                resultSet.getString("name"),
                                resultSet.getString("password"),
                                resultSet.getString("email"));
                    }
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
       }
        return user;
    }

    @Override
    public void createUser(UserDataType userData) throws DataAccessException {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement(
                    "INSERT INTO %s (name, password, email) values (?,?,?)".formatted(tableName))) {
                statement.setString(1, userData.getUserName());
                statement.setString(2, userData.getPassword());
                statement.setString(3, userData.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
