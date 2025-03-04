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
    public void newGame(GameDataType gameData){
        dataBase.addGameData(gameData);
    }
    public GameDataType findGame(int gameId){
        return dataBase.findGame(gameId);
    }
    public void updateGameData(GameDataType oldData, GameDataType newData){
        dataBase.updateGameData(oldData, newData);
    }
}
