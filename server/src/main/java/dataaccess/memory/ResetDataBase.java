package dataaccess.memory;

import memorydatabase.DataBase.ClearDataBase;

public class ResetDataBase {
    final ClearDataBase clearer;
    public ResetDataBase(ClearDataBase clearer){
        this.clearer = clearer;
    }
    private void deleteUsers(){
        clearer.clearUserData();
    }
    private void deleteAuth(){
        clearer.clearAuthData();
    }
    private void deleteGames(){
        clearer.clearGameData();
    }
    public void run(){
        deleteUsers();
        deleteAuth();
        deleteGames();
    }
}
