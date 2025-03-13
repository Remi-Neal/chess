package dataaccess.interfaces;

import dataaccess.DataAccessException;
import datatypes.GameDataType;

import java.util.List;

public interface GameDAO {
    List<GameDataType> gameList() throws DataAccessException;
    void newGame(GameDataType gameData) throws DataAccessException;
    GameDataType findGame(int gameId) throws DataAccessException;
    void updateGameData(GameDataType oldData, GameDataType newData) throws DataAccessException;
}
