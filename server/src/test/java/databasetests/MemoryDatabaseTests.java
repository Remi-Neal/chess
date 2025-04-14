package databasetests;

import chess.ChessGame;
import memorydatabase.DataBase;
import datatypes.AuthtokenDataType;
import datatypes.GameDataType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MemoryDatabaseTests {
    DataBase db = new DataBase();

    @Test
    public void assertAuthPositive(){
        AuthtokenDataType auth = new AuthtokenDataType("1","1");
        DataBase.AuthDataBase authdb = db.new AuthDataBase();
        authdb.addAuthData(auth);
        assert authdb.getAuthData("1") == auth;
        authdb.removeAuthData("1");
        assert authdb.getAuthData("1") != auth;
    }

    @Test
    public void assertAuthNegative(){
        AuthtokenDataType auth = new AuthtokenDataType("1","1");
        DataBase.AuthDataBase authdb = db.new AuthDataBase();
        authdb.addAuthData(auth);
        assert authdb.getAuthData("2") != auth;
        authdb.removeAuthData("2");
        assert authdb.getAuthData("1") == auth;
    }

    @Test
    public void assertGamePositive(){
        DataBase.GameDataBase gamedb = db.new GameDataBase();
        GameDataType game = new GameDataType(1, null, null, "1", new ChessGame(), true);
        gamedb.addGameData(game);
        List<GameDataType> gameList = new ArrayList<>();
        gameList.add(game);
        assert gamedb.listGame() == gameList;
        assert gamedb.findGame(1) == game;
        GameDataType updateedGame = new GameDataType(game.gameID(), "white", null, game.gameName(), game.chessGame(), true);
        gamedb.updateGameData(game, updateedGame);
        assert gamedb.listGame().isEmpty();
    }

}
