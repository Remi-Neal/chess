package dataaccess.authdata;

import database.DataBase.AuthDataBase;
import database.datatypes.AuthtokenDataType;

public class AuthMemoryDAO implements AuthDAO {
    //TODO: Implement AuthMemoryDAO
    //TODO: Create tests for AuthMemoryDAO
    private final AuthDataBase dataBase;
    public AuthMemoryDAO(AuthDataBase db){
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
