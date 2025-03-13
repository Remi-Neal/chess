package dataaccess.memory;

import dataaccess.interfaces.UserDAO;
import memorydatabase.DataBase.UserDataBase;
import datatypes.UserDataType;

public final class UserMemoryDAO implements UserDAO {
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
