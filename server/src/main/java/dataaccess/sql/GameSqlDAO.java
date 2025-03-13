package dataaccess.sql;

import dataaccess.interfaces.GameDAO;
import datatypes.GameDataType;

import java.util.List;

public class GameSqlDAO implements GameDAO {
    //TODO: implement gameList in GameSqlDAO
    @Override
    public List<GameDataType> gameList() {
        return List.of();
    }

    //TODO: implement newGame in GameSqlDAO
    @Override
    public void newGame(GameDataType gameData) {

    }

    //TODO: implement findGame in GameSqlDAO
    @Override
    public GameDataType findGame(int gameId) {
        return null;
    }

    //TODO: implement updateGameData in GameSqlDAO
    @Override
    public void updateGameData(GameDataType oldData, GameDataType newData) {

    }
}
