package dataaccess.userdata;

import memorydatabase.datatypes.UserDataType;

public interface UserDAO {
    UserDataType getUser(String name);
    void createUser(UserDataType userData);
}
