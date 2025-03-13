package dataaccess.sql;

import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;

public class UserSqlDAO implements UserDAO {
    public UserSqlDAO(String tableName){
    }

    //TODO: implement getUser in UserSqlDAO
    @Override
    public UserDataType getUser(String name) {
        return null;
    }

    //TODO: implement createUser in UserSqlDAO
    @Override
    public void createUser(UserDataType userData) {

    }
}
