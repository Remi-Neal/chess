package dataaccess;

import database.DataBase;
import dataaccess.userdata.UserDOA;
import dataaccess.gamedata.GameDOA;
import dataaccess.authdata.AuthDOA;
import dataaccess.cleardata.ClearDataBase;

import javax.xml.crypto.Data;

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
}
