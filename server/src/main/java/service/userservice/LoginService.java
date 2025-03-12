package service.userservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.userdata.UserDAO;
import memorydatabase.datatypes.AuthtokenDataType;
import memorydatabase.datatypes.UserDataType;
import service.exceptions.BadRequest;
import service.exceptions.Unauthorized;
import service.ServiceInterface;
import service.userservice.methods.Authenticator;
import service.userservice.methods.UserValidator;

public class LoginService implements ServiceInterface {
    public static AuthtokenDataType login(UserDAO userDAO, AuthDAO authDAO, UserDataType userData){
        if(userData.password() == null | userData.userName() == null){ throw new BadRequest(); }
        if(!UserValidator.validateUserData(userDAO, userData)){ throw new Unauthorized(); }
        return Authenticator.addAuth(authDAO, userData.getUserName());
    }
}
