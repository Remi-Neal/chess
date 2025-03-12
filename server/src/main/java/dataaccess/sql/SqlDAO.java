package dataaccess.sql;

import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.DAO;
import dataaccess.interfaces.GameDAO;

public class SqlDAO implements DAO {

    //TODO: Create user dao that only accesses sql table user_data
    @Override
    public UserDAO makeUserDAO() {
        return null;
    }

    //TODO: Create user dao that only accesses sql table game_data
    @Override
    public GameDAO makeGameDAO() {
        return null;
    }

    //TODO: Create user dao that only aesses sql table auth_data
    @Override
    public AuthDAO makeAuthDAO() {
        return null;
    }
}
