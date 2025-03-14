package dataaccess;

import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.DAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.memory.AuthMemoryDAO;
import dataaccess.memory.GameMemoryDAO;
import dataaccess.memory.ResetDataBase;
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

    public ResetDataBase makeClearer(){
        DataBase.ClearDataBase clearer = db.new ClearDataBase();
        return new ResetDataBase(clearer);
    }
}
