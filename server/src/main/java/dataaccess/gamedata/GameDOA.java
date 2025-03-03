package dataaccess.gamedata;

import database.DataBase.GameDataBase;
import database.datatypes.GameDataType;

import java.util.List;

public class GameDOA {
    //TODO: write tests for GameDOA
    private final GameDataBase dataBase;
    public GameDOA(GameDataBase db){
        dataBase = db;
    }
    public List<GameDataType> gameList(){
        return dataBase.listGame();
    }
    public void newGame(int gameId, String gameName){
        dataBase.addGameData(new GameDataType(gameId, gameName));
    }
    public GameDataType findGame(int gameId){
        return dataBase.findGame(gameId);
    }
    public void updateGameData(GameDataType gameData){
        dataBase.updateGameData(gameData);
    }
}
