package dataaccess.authdata;

import database.DataBase.AuthDataBase;
import database.datatypes.AuthtokenDataType;

public class AuthDOA {
    //TODO: Implement AuthDOA
    //TODO: Create tests for AuthDOA
    private final AuthDataBase dataBase;
    public AuthDOA(AuthDataBase db){
        dataBase = db;
    }
    public final AuthtokenDataType getAuth(String token){
        return dataBase.getAuthData(token);
    }
    public final void createAuth(AuthtokenDataType auth){
        dataBase.addAuthData(auth);
    }
    public final void removeAuth(String token){
        dataBase.removeAuthData(token);
    }
}
