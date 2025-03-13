package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserSqlDAO implements UserDAO {
    private String TABLE_NAME;
    public UserSqlDAO(String tableName){ TABLE_NAME = tableName; }

    @Override
    public UserDataType getUser(String name) throws DataAccessException {
        UserDataType user = null;
        try{
            var conn = DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + "WHERE name == ?")){
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
            var conn = DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    "INSERT INTO %s (name, password, email) values (?,?,?)".formatted(TABLE_NAME))) {
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
