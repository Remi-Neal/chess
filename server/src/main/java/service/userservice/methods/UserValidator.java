package service.userservice.methods;

import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;

public class UserValidator {
    public static Boolean validateUserData(UserDAO userDAO, UserDataType userData){
        UserDataType userInfo = userDAO.getUser(userData.userName());
        if(userInfo == null){ return false; }
        return userInfo.password().equals(userData.password());
    }

    public static Boolean isUniqueUserName(UserDAO userDAO, String userName){
        UserDataType userInfo = userDAO.getUser(userName);
        return userInfo == null;
    }
}
