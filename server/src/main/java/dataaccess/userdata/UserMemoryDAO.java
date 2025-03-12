package dataaccess.userdata;

import memorydatabase.DataBase.UserDataBase;
import memorydatabase.datatypes.UserDataType;

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
