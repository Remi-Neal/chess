package dataaccess.interfaces;

import datatypes.UserDataType;

public interface UserDAO {
    UserDataType getUser(String name);
    void createUser(UserDataType userData);
}
