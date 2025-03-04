package service.userservice;

import java.util.UUID;
import dataaccess.authdata.AuthDAO;
import dataaccess.userdata.UserDAO;
import database.datatypes.AuthtokenDataType;
import database.datatypes.UserDataType;
import service.ServiceInterface;

public class RegistrationService implements ServiceInterface {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    public RegistrationService(){
        userDAO = ServiceInterface.daoRecord.getUserDAO();
        authDAO = ServiceInterface.daoRecord.getAuthDAO();
    }

    private Boolean validNewUser(String userName){
        UserDataType userData = userDAO.getUser(userName);
        return userData.getUserName().equals(userName);
    }
    public AuthtokenDataType register(UserDataType registerRequest){
        if(validNewUser(registerRequest.getUserName())){
            userDAO.createUser(registerRequest);
            AuthtokenDataType authtoken = new AuthtokenDataType(
                    UUID.randomUUID().toString(),
                    registerRequest.getUserName());
            authDAO.createAuth(authtoken);
        }
        return null;
    }
}
