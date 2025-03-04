package dataaccess;

import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import dataaccess.userdata.UserDAO;

public interface DAO {
    public UserDAO makeUserDAO();
    public GameDAO makeGameDAO();
    public AuthDAO makeAuthDAO();
}
