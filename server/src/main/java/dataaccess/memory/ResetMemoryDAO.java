package dataaccess.memory;

import dataaccess.interfaces.ResetDAO;
import memorydatabase.DataBase;
import memorydatabase.DataBase.ClearDataBase;

public class ResetMemoryDAO implements ResetDAO {
    final ClearDataBase clearer;
    public ResetMemoryDAO(DataBase db){
        clearer = db.new ClearDataBase();
    }
    public void deleteUsers(){
        clearer.clearUserData();
    }
    public void deleteAuth(){
        clearer.clearAuthData();
    }
    public void deleteGames(){
        clearer.clearGameData();
    }
    public void run(){
        deleteUsers();
        deleteAuth();
        deleteGames();
    }
}
