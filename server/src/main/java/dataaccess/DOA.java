package dataaccess;

import dataaccess.resetdata.ResetDataBase;
import database.DataBase;
import dataaccess.userdata.UserDOA;
import dataaccess.gamedata.GameDOA;

public class DOA{
    static protected DataBase db;
    public DOA(){
        db = new DataBase();
    }

    public UserDOA makeUserDOA(){
        DataBase.UserDataBase userDB = db.new UserDataBase();
        return new UserDOA(userDB);
    }
    public GameDOA makeGameDOA(){
        DataBase.GameDataBase gameDB = db.new GameDataBase();
        return new GameDOA(gameDB);
    }
    public ResetDataBase makeClearer(){
        DataBase.ClearDataBase clearer = db.new ClearDataBase();
        return new ResetDataBase(clearer);
    }
}
