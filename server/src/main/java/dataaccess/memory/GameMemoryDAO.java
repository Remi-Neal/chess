package dataaccess.memory;

import dataaccess.interfaces.GameDAO;
import memorydatabase.DataBase.GameDataBase;
import datatypes.GameDataType;

import java.util.List;

public class GameMemoryDAO implements GameDAO {
    private final GameDataBase dataBase;
    public GameMemoryDAO(GameDataBase db){
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
