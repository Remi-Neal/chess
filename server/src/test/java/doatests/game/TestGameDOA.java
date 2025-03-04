package doatests.game;

import dataaccess.DOA;
import dataaccess.gamedata.GameDOA;
import dataaccess.resetdata.ResetDataBase;
import database.datatypes.GameDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGameDOA {
    GameDOA gameDOA;
    ResetDataBase reset;
    public TestGameDOA(){
        DOA doa = new DOA();
        gameDOA = doa.makeGameDOA();
        reset = doa.makeClearer();
    }

    @BeforeEach
    public void reset(){
        reset.run();
    }

    @Test
    public void positiveTestGameDOA(){
        GameDataType hitch = new GameDataType(42, "Hitchhiker's");
        gameDOA.newGame(hitch);
        GameDataType doaReturn = gameDOA.findGame(42);
        Assertions.assertEquals(hitch, doaReturn, "Positive return game failed!");

        GameDataType newHitch = new GameDataType(42, "Hitchhiker's");
        newHitch.setWhite("Arthur");
        newHitch.setBlack("Marvin");
        gameDOA.updateGameData(hitch, newHitch);
        doaReturn = gameDOA.findGame(42);
        Assertions.assertEquals(newHitch, doaReturn, "Positive update game failed!");

        GameDataType friday = new GameDataType(13, "Friday");
        gameDOA.newGame(friday);
        List<GameDataType> testGameList = new ArrayList<>();
        testGameList.add(newHitch);
        testGameList.add(friday);
        List<GameDataType> listReturn = gameDOA.gameList();
        Assertions.assertEquals(listReturn, testGameList, "Lists are not the same!");
    }

    @Test
    public void negativeTestGameDOA(){
        GameDataType hitch = new GameDataType(42, "Hitchhiker's");
        gameDOA.newGame(hitch);
        GameDataType doaReturn = gameDOA.findGame(420);
        Assertions.assertNotEquals(hitch, doaReturn, "Negative return game failed!");

        GameDataType newHitch = new GameDataType(42, "Hitchhiker's");
        newHitch.setWhite("Arthur");
        newHitch.setBlack("Marvin");
        gameDOA.updateGameData(hitch, newHitch);
        doaReturn = gameDOA.findGame(42);
        Assertions.assertNotEquals(hitch, doaReturn, "Negative update game failed!");

        GameDataType friday = new GameDataType(13, "Friday");
        gameDOA.newGame(friday);
        List<GameDataType> testGameList = new ArrayList<>();
        testGameList.add(hitch);
        testGameList.add(friday);
        List<GameDataType> listReturn = gameDOA.gameList();
        Assertions.assertNotEquals(listReturn, testGameList, "Lists are the same!");
    }
}
