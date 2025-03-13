package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserSqlDAO implements UserDAO {
    private String TABLE_NAME;
    public UserSqlDAO(String tableName){ TABLE_NAME = tableName; }

    //TODO: implement getUser in UserSqlDAO
    @Override
    public UserDataType getUser(String name) {
        return null;
    }

    //TODO: implement createUser in UserSqlDAO
    @Override
    public void createUser(UserDataType userData) throws DataAccessException {
        try{
            var conn =  DatabaseManager.getConnection();
            try(var statement = conn.prepareStatement(
                    "INSERT INTO" + TABLE_NAME + "(name, password, email) values (?,?,?)");) {
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
