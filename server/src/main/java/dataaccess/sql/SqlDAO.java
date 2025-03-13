package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.DAO;
import dataaccess.interfaces.GameDAO;

public class SqlDAO implements DAO {

    public SqlDAO() throws DataAccessException { DatabaseManager.createDatabase(); }

    @Override
    public UserDAO makeUserDAO() {
        return new UserSqlDAO(DatabaseManager.USER_TABLE_NAME); // Hard coded but could be parameterized
    }

    @Override
    public GameDAO makeGameDAO() {
        return new GameSqlDAO(DatabaseManager.GAME_TABLE_NAME);
    }

    @Override
    public AuthDAO makeAuthDAO() {
        return new AuthSqlDAO(DatabaseManager.AUTH_TABLE_NAME);
    }
}
