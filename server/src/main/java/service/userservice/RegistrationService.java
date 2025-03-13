package service.userservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import service.exceptions.BadRequest;
import service.userservice.methods.Authenticator;
import service.userservice.methods.UserValidator;
import org.mindrot.jbcrypt.BCrypt;

public class RegistrationService {
    public AuthtokenDataType register(
            UserDAO userDAO, AuthDAO authDAO, UserDataType registerRequest) throws DataAccessException {
        if(registerRequest.userName() == null | registerRequest.password() == null){ throw new BadRequest(); }
        String userName = registerRequest.getUserName();
        if(UserValidator.isUniqueUserName(userDAO,userName)){
            UserDataType registerWithHashedPassword = new UserDataType(
                    registerRequest.getUserName(),
                    BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()),
                    registerRequest.getEmail());
            userDAO.createUser(registerWithHashedPassword);
            return Authenticator.addAuth(authDAO, userName);
        }
        return null;
    }
}
