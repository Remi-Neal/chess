package dataaccess;

import dataaccess.interfaces.*;
import dataaccess.memory.AuthMemoryDAO;
import dataaccess.memory.GameMemoryDAO;
import dataaccess.memory.ResetMemoryDAO;
import dataaccess.memory.UserMemoryDAO;
import memorydatabase.DataBase;

public class MemoryDAO implements DAO {
    static protected DataBase db;
    public MemoryDAO(){
        db = new DataBase();
    }

    public UserDAO makeUserDAO(){
        DataBase.UserDataBase userDB = db.new UserDataBase();
        return new UserMemoryDAO(userDB);
    }
    public GameDAO makeGameDAO(){
        DataBase.GameDataBase gameDB = db.new GameDataBase();
        return new GameMemoryDAO(gameDB);
    }
    public AuthDAO makeAuthDAO(){
        DataBase.AuthDataBase authDB = db.new AuthDataBase();
        return new AuthMemoryDAO(authDB);
    }

    @Override
    public ResetDAO makeResetDAO() {
       return new ResetMemoryDAO(db);
    }
}
