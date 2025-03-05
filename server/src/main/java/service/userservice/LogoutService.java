package service.userservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.userdata.UserDAO;
import database.datatypes.AuthtokenDataType;
import service.userservice.methods.Authenticator;

public class LogoutService {
    public static boolean logout(AuthDAO authDAO, AuthtokenDataType authtoken){
        String authID = authtoken.getAuthToken();
        if(Authenticator.validAuth(authDAO, authID)){
            authDAO.removeAuth(authID);
            return true;
        }
        return false;
    }
}
