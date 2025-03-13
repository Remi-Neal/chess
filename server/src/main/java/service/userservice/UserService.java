package service.userservice;

import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import service.ServiceInterface;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    public UserService(){
        this.userDAO = ServiceInterface.DAO_RECORD.getUserDAO();
        this.authDAO = ServiceInterface.DAO_RECORD.getAuthDAO();
    }


    public AuthtokenDataType register(UserDataType registrationRequest){
        RegistrationService registration = new RegistrationService();
        return registration.register(userDAO, authDAO, registrationRequest);
    }

    public AuthtokenDataType login(UserDataType loginRequest){
        return LoginService.login(userDAO, authDAO, loginRequest);
    }

    public boolean logout(AuthtokenDataType authtoken){
        return LogoutService.logout(authDAO, authtoken);
    }
}
