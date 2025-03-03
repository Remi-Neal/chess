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

    public class AuthDataBase {
        public void addAuthData(AuthtokenData data){
            authData.add(data);
        }
        public void removeAuthData(String data) {
            for (AuthtokenData token : authData) {
                if (token.getAuthToken().equals(data)) {
                    authData.remove(token);
                    break;
                }
            }
        }
        public Boolean validAuthData(String data){
            for(AuthtokenData token : authData){
                if(token.getAuthToken().equals(data)) return true;
            }
            return false;
        }
    }

    public class GameDataBase{
        public void addGameData(GameData data){
            gameData.add(data);
        }
        public void updateGameData(GameData data) {
            for (GameData game : gameData) {
                if (game.getGameId() == data.getGameId()) {
                    gameData.remove(game);
                    gameData.add(data);
                }
            }
        }
        public List<GameData> listGame(){
            return gameData;
        }
        public GameData findGame(int data){
            for(GameData game : gameData){
                if(game.getGameId() == data){
                    return game;
                }
            }
            return null;
        }
    }

    public class UserDataBase{
        public void addUserData(UserData data){
            userData.add(data);
        }
        public UserData getUserData(String name){
            for(UserData user : userData){
                if(user.getUserName().equals(name)) return user;
            }
            return null;
        }

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

