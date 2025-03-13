package service.userservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import service.exceptions.BadRequest;
import service.exceptions.Unauthorized;
import service.userservice.methods.Authenticator;
import service.userservice.methods.UserValidator;

public class LoginService {
    public static AuthtokenDataType login(UserDAO userDAO, AuthDAO authDAO, UserDataType userData) throws DataAccessException {
        if(userData.password() == null | userData.userName() == null){ throw new BadRequest(); }
        if(!UserValidator.validateUserData(userDAO, userData)){ throw new Unauthorized(); }
        return Authenticator.addAuth(authDAO, userData.getUserName());
    }
}
