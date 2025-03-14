package dataaccess.interfaces;

import dataaccess.DataAccessException;
import memorydatabase.DataBase;

public interface ResetDAO {
    void deleteUsers() throws DataAccessException;
    void deleteAuth() throws DataAccessException;
    void deleteGames() throws DataAccessException;
    void run() throws DataAccessException;
}
