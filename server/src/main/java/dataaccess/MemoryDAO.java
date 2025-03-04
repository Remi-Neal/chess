package dataaccess;

import dataaccess.authdata.AuthDAO;
import dataaccess.authdata.AuthMemoryDAO;
import dataaccess.gamedata.GameDAO;
import dataaccess.gamedata.GameMemoryDAO;
import dataaccess.resetdata.ResetDataBase;
import dataaccess.userdata.UserDAO;
import dataaccess.userdata.UserMemoryDAO;
import database.DataBase;

public class MemoryDAO implements DAO{
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
