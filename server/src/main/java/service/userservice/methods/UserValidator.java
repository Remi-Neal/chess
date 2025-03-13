package service.userservice.methods;

import dataaccess.interfaces.UserDAO;
import datatypes.UserDataType;
import org.mindrot.jbcrypt.BCrypt;

public class UserValidator {
    public static Boolean validateUserData(UserDAO userDAO, UserDataType userData){
        UserDataType userInfo = userDAO.getUser(userData.userName());
        if(userInfo == null){ return false; }
        return BCrypt.checkpw(userData.getPassword(), userInfo.password());
    }

    public static Boolean isUniqueUserName(UserDAO userDAO, String userName){
        UserDataType userInfo = userDAO.getUser(userName);
        return userInfo == null;
    }
}
