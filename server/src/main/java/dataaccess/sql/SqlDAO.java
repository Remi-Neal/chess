package dataaccess.sql;

import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.DAO;
import dataaccess.interfaces.GameDAO;

public class SqlDAO implements DAO {


    @Override
    public UserDAO makeUserDAO() {
        return new UserSqlDAO("user"); // Hard coded but could be parameterized
    }

    @Override
    public GameDAO makeGameDAO() {
        return new GameSqlDAO("game");
    }

    @Override
    public AuthDAO makeAuthDAO() {
        return new AuthSqlDAO("auth");
    }
}
