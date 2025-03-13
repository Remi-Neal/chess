package dataaccess.interfaces;

import dataaccess.DataAccessException;
import datatypes.UserDataType;

public interface UserDAO {
    UserDataType getUser(String name) throws DataAccessException;
    void createUser(UserDataType userData) throws DataAccessException;
}
