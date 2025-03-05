package service.userservice.methods;

import dataaccess.userdata.UserDAO;
import database.datatypes.UserDataType;

public class ValidateUser {
    public static Boolean validateUserData(UserDAO userDAO, UserDataType userData){
        UserDataType userInfo = userDAO.getUser(userData.userName());
        return userInfo.equals(userData);
    }

    public static Boolean isUniqueUserName(UserDAO userDAO, String userName){
        UserDataType userInfo = userDAO.getUser(userName);
        return userInfo == null;
    }
}
