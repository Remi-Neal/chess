package dataaccess.authdata;

import memorydatabase.datatypes.AuthtokenDataType;

public interface AuthDAO {
    void createAuth(AuthtokenDataType auth);
    void removeAuth(String token);
    AuthtokenDataType getAuth(String token);
}
