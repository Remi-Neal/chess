package dataaccess.userdata;

import database.DataBase.UserDataBase;
import database.datatypes.UserDataType;

public final class UserMemoryDAO implements UserDAO {
    //TODO: write tests for UserMemoryDAO
    private final UserDataBase dataBase;
    public UserMemoryDAO(UserDataBase db){
        dataBase = db;
    }
    public UserDataType getUser(String name){
        return dataBase.getUserData(name);
    }
    public void createUser(UserDataType userData){
        dataBase.addUserData(userData);
    }
}
