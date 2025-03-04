package dataaccess.userdata;

import database.datatypes.UserDataType;

public interface UserDAO {
    UserDataType getUser(String name);
    void createUser(UserDataType userData);
}
