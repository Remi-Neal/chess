package dataaccess.userdata;

import database.DataBase.UserDataBase;
import database.datatypes.UserDataType;

public final class UserDOA {
    //TODO: write tests for UserDOA
    private final UserDataBase dataBase;
    public UserDOA(UserDataBase db){
        dataBase = db;
    }
    public UserDataType getUser(String name){
        return dataBase.getUserData(name);
    }
    public void createUser(UserDataType userData){
        dataBase.addUserData(userData);
    }
}
