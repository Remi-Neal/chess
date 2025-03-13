package service.userservice;

import dataaccess.interfaces.AuthDAO;
import datatypes.AuthtokenDataType;
import service.userservice.methods.Authenticator;

public class LogoutService {
    public static boolean logout(AuthDAO authDAO, AuthtokenDataType authtoken){
        String authID = authtoken.authToken();
        if(Authenticator.validAuth(authDAO, authID)){
            authDAO.removeAuth(authID);
            return true;
        }
        return false;
    }
}
