package service.userservice;

import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import service.exceptions.BadRequest;
import service.userservice.methods.Authenticator;
import service.userservice.methods.UserValidator;

public class RegistrationService {
    public AuthtokenDataType register(UserDAO userDAO, AuthDAO authDAO, UserDataType registerRequest){
        if(registerRequest.userName() == null | registerRequest.password() == null){ throw new BadRequest(); }
        String userName = registerRequest.getUserName();
        if(UserValidator.isUniqueUserName(userDAO,userName)){
            userDAO.createUser(registerRequest);
            return Authenticator.addAuth(authDAO, userName);
        }
        return null;
    }
}
