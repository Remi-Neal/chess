package service.userservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import service.DAORecord;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    public UserService(DAORecord daoRecord){
        this.userDAO = daoRecord.getUserDAO();
        this.authDAO = daoRecord.getAuthDAO();
    }


    public AuthtokenDataType register(UserDataType registrationRequest) throws DataAccessException {
        RegistrationService registration = new RegistrationService();
        return registration.register(userDAO, authDAO, registrationRequest);
    }

    public AuthtokenDataType login(UserDataType loginRequest) throws DataAccessException {
        return LoginService.login(userDAO, authDAO, loginRequest);
    }

    public boolean logout(AuthtokenDataType authtoken) throws DataAccessException {
        return LogoutService.logout(authDAO, authtoken);
    }

    public String getUserName(String authToken) throws DataAccessException {
        AuthtokenDataType authData = authDAO.getAuth(authToken);
        if(authData != null) {
            return authData.username();
        } else {
            return null;
        }
    }
}
