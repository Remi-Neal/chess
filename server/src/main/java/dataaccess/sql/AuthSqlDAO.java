package dataaccess.sql;

import dataaccess.interfaces.AuthDAO;
import datatypes.AuthtokenDataType;

public class AuthSqlDAO implements AuthDAO {
    public AuthSqlDAO(String tableName){
    }

    //TODO: implement createAuth in AuthSqlDAO
    @Override
    public void createAuth(AuthtokenDataType auth) {

    }

    //TODO: implement removeAuth in AuthSqlDAO
    @Override
    public void removeAuth(String token) {

    }

    //TODO: implement getAuth in AuthSqlDAO
    @Override
    public AuthtokenDataType getAuth(String token) {
        return null;
    }
}
