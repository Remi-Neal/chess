package dataaccess.interfaces;

import memorydatabase.datatypes.AuthtokenDataType;

public interface AuthDAO {
    void createAuth(AuthtokenDataType auth);
    void removeAuth(String token);
    AuthtokenDataType getAuth(String token);
}
