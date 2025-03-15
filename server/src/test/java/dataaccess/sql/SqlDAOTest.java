package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.ResetDAO;
import datatypes.AuthtokenDataType;
import datatypes.GameDataType;
import datatypes.UserDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SqlDAOTest {
    private static final SqlDAO SQL_DAO = new SqlDAO();
    private static final String AUTH_TABLE = "auth";
    private static final String USER_TABLE = "user";
    private static final String GAME_TABLE = "game";

    @BeforeEach
    void setUp() {
        ResetDAO resetDAO = SQL_DAO.makeResetDAO();
        try {
            resetDAO.run();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getConnection() {
        try {
            var conn = SqlDAO.getConnection();
            assert conn != null;
        } catch (DataAccessException e) {
            assert e.getClass() != DataAccessException.class;
        }

    }

    @Test
    void createTables() {
        try{
            var conn = SqlDAO.getConnection();
            try(var statement = conn.prepareStatement("SHOW TABLES")){
                var resultSet = statement.executeQuery();
                int length = 0;
                while(resultSet.next()){
                    length++;
                }
                assertEquals(3, length);
            }
        } catch(DataAccessException e){
            assert e.getClass() != DataAccessException.class;
        } catch(SQLException e){
            assert e.getClass() != SQLException.class;
        }
    }

    @Test
    @DisplayName("Create User DAO")
    void makeUserDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }

    @Test
    @DisplayName("Create Game DAO")
    void makeGameDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }

    @Test
    @DisplayName("Create Auth DAO")
    void makeAuthDAO() {
        AuthSqlDAO authSqlDAO = new AuthSqlDAO("auth");
        assert authSqlDAO != null;
    }

    @Test
    @DisplayName("Create Reset DAO")
    void makeResetDAO() {
        GameSqlDAO gameSqlDAO = new GameSqlDAO("game");
        assert gameSqlDAO != null;
    }

    /*
        AuthSqlDAO tests
     */

    @Test
    @DisplayName("Create Auth")
    void createAuth() {
        AuthSqlDAO authSqlDAO = new AuthSqlDAO("auth");
        String authToken = "1";
        String name = "name";
        // Positive
        try {
            AuthtokenDataType authData = new AuthtokenDataType(authToken,name);
            authSqlDAO.createAuth(authData);
            assert authSqlDAO.getAuth("1").equals(authData);
        } catch(DataAccessException e){
            assert e.getClass() != DataAccessException.class;
        }
        // Negative
        try{
            AuthtokenDataType badData = new AuthtokenDataType(authToken,name);
            authSqlDAO.createAuth(badData);
        } catch(DataAccessException e){
            assert e.getClass() == DataAccessException.class;
        }

    }

    @Test
    @DisplayName("Remove Auth")
    void removeAuth() {
        String authToken = "1";
        String name = "name";
        AuthtokenDataType authData = new AuthtokenDataType(authToken,name);
        AuthSqlDAO authSqlDAO = new AuthSqlDAO(AUTH_TABLE);
        // Positive
        assertDoesNotThrow(()->{
            authSqlDAO.createAuth(authData);
            assert authSqlDAO.getAuth(authToken).equals(authData);
            authSqlDAO.removeAuth(authToken);
            assert authSqlDAO.getAuth(authToken) == null;
                });
        // Negative
        assertDoesNotThrow(()->{
            AuthtokenDataType nullAuth = new AuthtokenDataType("null", "null");
            authSqlDAO.createAuth(nullAuth);
            assert authSqlDAO.getAuth("null").equals(nullAuth);
            authSqlDAO.removeAuth(null);
            assert authSqlDAO.getAuth("null").equals(nullAuth);
        });
    }

    @Test
    void getAuth() {
        String authToken = "1";
        String name = "name";
        AuthtokenDataType authData = new AuthtokenDataType(authToken, name);
        AuthSqlDAO authSqlDAO = new AuthSqlDAO(AUTH_TABLE);
        // Positive
        assertDoesNotThrow(()->{
            authSqlDAO.createAuth(authData);
            assert authSqlDAO.getAuth(authToken).equals(authData);
        });
        // Negative
        assertDoesNotThrow(()->{
            assert authSqlDAO.getAuth("2") != authData;
        });
    }

    /*
        UserSqlDAO tests
     */

    @Test
    void getUser() {
        String name = "name";
        String password = "password";
        String email = "email";
        UserDataType userDataType = new UserDataType(name,password,email);
        UserSqlDAO userSqlDAO = new UserSqlDAO(USER_TABLE);
        // Positive
        assertDoesNotThrow(()->{
            userSqlDAO.createUser(userDataType);
            UserDataType returned = userSqlDAO.getUser(name);
            assert userDataType.equals(returned);
        });
        // Negative
        assertDoesNotThrow(()->{
           UserDataType returned = userSqlDAO.getUser("poo");
           assert userDataType != returned;
        });
    }

    @Test
    @DisplayName("Create new User")
    void createUser() {
       String name = "name";
       String password = "password";
       String email = "email";
       UserDataType userDataType = new UserDataType(name, password, email);
       UserSqlDAO userSqlDAO = new UserSqlDAO(USER_TABLE);
       // Positive
       assertDoesNotThrow(()->{
           userSqlDAO.createUser(userDataType);
           assert userSqlDAO.getUser(name).equals(userDataType);
       });
       assertDoesNotThrow(()-> userSqlDAO.createUser(new UserDataType(name, password, null)));
       // Negative
       assertThrows(DataAccessException.class,()-> userSqlDAO.createUser(new UserDataType(
               null,null,email)));
       assertThrows(DataAccessException.class, ()-> userSqlDAO.createUser(new UserDataType(
               null, password, email)));

    }

    /*
        GameSql tests
     */

    @Test
    void gameList() {
        String gameName = "game";
        int id = 1;
        GameDataType gameData = new GameDataType(id, null, null, gameName);
        GameSqlDAO gameSqlDAO = new GameSqlDAO(GAME_TABLE);
        // Negative
        assertDoesNotThrow(()->{
            assert gameSqlDAO.gameList().isEmpty();
        });
        // Positive
        assertDoesNotThrow(()->{
            gameSqlDAO.newGame(gameData);
            assert gameSqlDAO.gameList().size() == 1;
        });
        // Negative
        assertDoesNotThrow(()->{
            ResetDAO resetDAO = new ResetSQLDatabase(USER_TABLE, GAME_TABLE, AUTH_TABLE);
            resetDAO.deleteGames();
            assert gameSqlDAO.gameList().isEmpty();
        });
    }

    @Test
    void newGame() {
        String gameName = "game";
        int id = 1;
        GameDataType gameData = new GameDataType(id, null, null, gameName);
        GameSqlDAO gameSqlDAO = new GameSqlDAO(GAME_TABLE);
        // Positive
        assertDoesNotThrow(()->{
           gameSqlDAO.newGame(gameData);
           assert gameSqlDAO.findGame(id).equals(gameData);
        });
        // Negative
        assertThrows(DataAccessException.class, ()-> gameSqlDAO.newGame(new GameDataType(
                id, null, null, null)));
    }

    @Test
    void findGame() {
        String gameName = "game";
        int id = 1;
        GameDataType gameData = new GameDataType(id, null, null, gameName);
        GameSqlDAO gameSqlDAO = new GameSqlDAO(GAME_TABLE);
        // Positive
        assertDoesNotThrow(()->{
            gameSqlDAO.newGame(gameData);
            assert gameSqlDAO.findGame(id).equals(gameData);
        });
        // Negative
        assertDoesNotThrow(()->{
           assert gameSqlDAO.findGame(2) != gameData;
        });
    }

    @Test
    void updateGameData() {
        String gameName = "game";
        int id = 1;
        GameDataType gameData = new GameDataType(id, null, null, gameName);
        GameSqlDAO gameSqlDAO = new GameSqlDAO(GAME_TABLE);
        // Positive Update Players
        assertDoesNotThrow(()->{
           gameSqlDAO.newGame(gameData);
           GameDataType newData = new GameDataType(id, "name", null, gameName);
           gameSqlDAO.updateGameData(gameData,newData);
           assert gameSqlDAO.findGame(id).equals(newData);
        });
        assertDoesNotThrow(()->{
            GameDataType newData = new GameDataType(id, "name", "other name", gameName);
            gameSqlDAO.updateGameData(gameData,newData);
            assert gameSqlDAO.findGame(id).equals(newData);
        });
       // Negative Update Players
        String whiteName = "name";
        String blackName = "other name";
        GameDataType currData = new GameDataType(id,whiteName,blackName,gameName);

        GameDataType finalCurrData1 = currData;
        assertDoesNotThrow(()->{
          GameDataType newData = new GameDataType(id, "new name", blackName, gameName);
          gameSqlDAO.updateGameData(finalCurrData1, newData);
          assert  gameSqlDAO.findGame(id).equals(finalCurrData1);
       });

        GameDataType finalCurrData = currData;
        assertDoesNotThrow(()->{
            GameDataType newData = new GameDataType(id, whiteName, "new name", gameName);
            gameSqlDAO.updateGameData(finalCurrData, newData);
            assert  gameSqlDAO.findGame(id).equals(finalCurrData);
        });
        // Positive Update Game and Game Name
        String newGameName = "new name";
        GameDataType finalCurrData2 = currData;
        assertDoesNotThrow(()->{
            GameDataType newData = new GameDataType(id, whiteName, blackName, newGameName);
            gameSqlDAO.updateGameData(finalCurrData2, newData);
            assert  gameSqlDAO.findGame(id).equals(newData);
        });
        currData = new GameDataType(id, whiteName,blackName,newGameName);
        GameDataType finalCurrData3 = currData;
        assertDoesNotThrow(()->{
            GameDataType newData = new GameDataType(id, whiteName,blackName,newGameName);
            newData.setGameBoard(new ChessGame());
            gameSqlDAO.updateGameData(finalCurrData3, newData);
        });
        // Negative Update Game and Game Name
        currData.setGameBoard(new ChessGame());
        GameDataType finalCurrData4 = currData;
        assertDoesNotThrow(()->{
            GameDataType newGame = new GameDataType(id,whiteName,blackName,newGameName);
            gameSqlDAO.updateGameData(finalCurrData4, newGame);
            assert gameSqlDAO.findGame(id).equals(finalCurrData4);
        });
        assertDoesNotThrow(()->{
            GameDataType newGame = new GameDataType(id,whiteName,blackName,null);
            gameSqlDAO.updateGameData(finalCurrData4,newGame);
            assert gameSqlDAO.findGame(id).equals(finalCurrData4);
        });
    }
}