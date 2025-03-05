package service.userservice;

import dataaccess.authdata.AuthDAO;
import dataaccess.userdata.UserDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.UserDataType;
import service.ServiceInterface;
import service.userservice.methods.Authenticator;
import service.userservice.methods.ValidateUser;

public class RegistrationService implements ServiceInterface {
    public AuthtokenDataType register(UserDAO userDAO, AuthDAO authDAO, UserDataType registerRequest){
        String userName = registerRequest.getUserName();
        if(ValidateUser.isUniqueUserName(userDAO,userName)){
            userDAO.createUser(registerRequest);
            return Authenticator.addAuth(authDAO, userName);
        }
        return null;
    }
}
