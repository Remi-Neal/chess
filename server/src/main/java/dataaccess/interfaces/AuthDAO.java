package dataaccess.interfaces;

import dataaccess.DataAccessException;
import datatypes.AuthtokenDataType;

public interface AuthDAO {
    void createAuth(AuthtokenDataType auth) throws DataAccessException;
    void removeAuth(String token) throws DataAccessException;
    AuthtokenDataType getAuth(String token) throws DataAccessException;
}
