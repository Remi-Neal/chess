package database;
import database.datamodels.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    static List<AuthtokenData> authData;
    static List<GameData> gameData;
    static List<UserData> userData;

    public DataBase(){
        authData = new ArrayList<>();
        gameData = new ArrayList<>();
        userData = new ArrayList<>();
    }

    public void addAuthData(AuthtokenData data){
        authData.add(data);
    }
    public void addGameData(GameData data){
        gameData.add(data);
    }
    public void addUserData(UserData data){
        userData.add(data);
    }

    public void updateAuthData(AuthtokenData data){
        throw new UnsupportedOperationException("DataBase.updateAuthData not implemented");
    }
    public void updateGameData(GameData data){
        throw new UnsupportedOperationException("DataBase.updateGameData not implemented");
    }
    public void updateUserData(UserData data){
        throw new UnsupportedOperationException("DataBase.updateUserData not implemented");
    }

    public AuthtokenData getAuthData(){
        throw new UnsupportedOperationException("DataBase.getAuthData not implemented");
    }
    public GameData getGameData(){
        throw new UnsupportedOperationException("DataBase.getAGameData not implemented");
    }
    public UserData getUserData(){
        throw new UnsupportedOperationException("DataBase.getUserData not implemented");
    }

    public static class ClearDataBase{
        public void clearAuthData(){
            authData.clear();
        }
        public void clearUserData(){
            userData.clear();
        }
        public void clearGameData(){
            gameData.clear();
        }
    }
}

