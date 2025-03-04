package dataaccess;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import dataaccess.userdata.UserDAO;

public interface DAO {
    UserDAO makeUserDAO();
    GameDAO makeGameDAO();
    AuthDAO makeAuthDAO();
}
