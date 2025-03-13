package dataaccess.interfaces;

import datatypes.GameDataType;

import java.util.List;

public interface GameDAO {
    List<GameDataType> gameList();
    void newGame(GameDataType gameData);
    GameDataType findGame(int gameId);
    void updateGameData(GameDataType oldData, GameDataType newData);
}
