package dataaccess.memory;

import dataaccess.interfaces.AuthDAO;
import memorydatabase.DataBase.AuthDataBase;
import datatypes.AuthtokenDataType;

public class AuthMemoryDAO implements AuthDAO {
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
