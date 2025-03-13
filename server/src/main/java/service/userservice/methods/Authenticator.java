package service.userservice.methods;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import datatypes.AuthtokenDataType;

import java.util.UUID;

public class Authenticator {
    public static AuthtokenDataType addAuth(AuthDAO authDAO, String userName) throws DataAccessException {
        AuthtokenDataType authData = new AuthtokenDataType(UUID.randomUUID().toString(), userName);
        authDAO.createAuth(authData);
        return authData;
    }
    public static boolean validAuth(AuthDAO authDAO, String authID) throws DataAccessException {
        AuthtokenDataType authData = authDAO.getAuth(authID);
        return authData != null;
    }
}
