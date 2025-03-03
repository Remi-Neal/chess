package database;
import database.datatypes.*;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    static List<AuthtokenDataType> authData;
    static List<GameDataType> gameData;
    static List<UserDataType> userData;

    public DataBase(){
        authData = new ArrayList<>();
        gameData = new ArrayList<>();
        userData = new ArrayList<>();
    }

    public class AuthDataBase {
        public void addAuthData(AuthtokenDataType data){
            authData.add(data);
        }
        public void removeAuthData(String data) {
            for (AuthtokenDataType token : authData) {
                if (token.getAuthToken().equals(data)) {
                    authData.remove(token);
                    break;
                }
            }
        }
        public Boolean validAuthData(String data){
            for(AuthtokenDataType token : authData){
                if(token.getAuthToken().equals(data)) return true;
            }
            return false;
        }
    }

    public class GameDataBase{
        public void addGameData(GameDataType data){
            gameData.add(data);
        }
        public void updateGameData(GameDataType data) {
            for (GameDataType game : gameData) {
                if (game.getGameId() == data.getGameId()) {
                    gameData.remove(game);
                    gameData.add(data);
                }
            }
        }
        public List<GameDataType> listGame(){
            return gameData;
        }
        public GameDataType findGame(int data){
            for(GameDataType game : gameData){
                if(game.getGameId() == data){
                    return game;
                }
            }
            return null;
        }
    }

    public class UserDataBase{
        public void addUserData(UserDataType data){
            userData.add(data);
        }
        public UserDataType getUserData(String name){
            for(UserDataType user : userData){
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

