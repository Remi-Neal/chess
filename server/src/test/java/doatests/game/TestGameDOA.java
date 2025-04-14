package doatests.game;

import chess.ChessGame;
import dataaccess.MemoryDAO;
import dataaccess.memory.GameMemoryDAO;
import dataaccess.memory.ResetMemoryDAO;
import datatypes.GameDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGameDOA {
    GameMemoryDAO gameDOA;
    ResetMemoryDAO reset;
    public TestGameDOA(){
        MemoryDAO doa = new MemoryDAO();
        gameDOA = (GameMemoryDAO) doa.makeGameDAO();
        reset = (ResetMemoryDAO) doa.makeResetDAO();
    }

    @BeforeEach
    public void reset(){
        reset.run();
    }

    @Test
    public void positiveTestGameDOA(){
        GameDataType hitch = new GameDataType(42, "","","Hitchhiker's", new ChessGame(), true);
        gameDOA.newGame(hitch);
        GameDataType doaReturn = gameDOA.findGame(42);
        Assertions.assertEquals(hitch, doaReturn, "Positive return game failed!");

        GameDataType newHitch = new GameDataType(42, "Arthur","Marvin","Hitchhiker's", new ChessGame(), true);
        gameDOA.updateGameData(hitch, newHitch);
        doaReturn = gameDOA.findGame(42);
        Assertions.assertEquals(newHitch, doaReturn, "Positive update game failed!");

        GameDataType friday = new GameDataType(13, "","","Friday", new ChessGame(), true);
        gameDOA.newGame(friday);
        List<GameDataType> testGameList = new ArrayList<>();
        testGameList.add(newHitch);
        testGameList.add(friday);
        List<GameDataType> listReturn = gameDOA.gameList();
        Assertions.assertEquals(listReturn, testGameList, "Lists are not the same!");
    }

    @Test
    public void negativeTestGameDOA(){
        GameDataType hitch = new GameDataType(42, "","","Hitchhiker's", new ChessGame(), true);
        gameDOA.newGame(hitch);
        GameDataType doaReturn = gameDOA.findGame(420);
        Assertions.assertNotEquals(hitch, doaReturn, "Negative return game failed!");

        GameDataType newHitch = new GameDataType(42, "Arthur","Marvin","Hitchhiker's", new ChessGame(), true);
        gameDOA.updateGameData(hitch, newHitch);
        doaReturn = gameDOA.findGame(42);
        Assertions.assertNotEquals(hitch, doaReturn, "Negative update game failed!");

        GameDataType friday = new GameDataType(13, "","","Friday", new ChessGame(), true);
        gameDOA.newGame(friday);
        List<GameDataType> testGameList = new ArrayList<>();
        testGameList.add(hitch);
        testGameList.add(friday);
        List<GameDataType> listReturn = gameDOA.gameList();
        Assertions.assertNotEquals(listReturn, testGameList, "Lists are the same!");
    }
}
