package dataaccess.sql;

import dataaccess.DatabaseManager;
import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.DAO;
import dataaccess.interfaces.GameDAO;

public class SqlDAO implements DAO {


    @Override
    public UserDAO makeUserDAO() {
        return new UserSqlDAO();
    }

    @Override
    public GameDAO makeGameDAO() {
        return new GameSqlDAO();
    }

    @Override
    public AuthDAO makeAuthDAO() {
        return new AuthSqlDAO();
    }
}
