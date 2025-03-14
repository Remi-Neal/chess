package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.ResetDAO;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SqlDAOTest {

    private static SqlDAO sqlDAO = new SqlDAO();
    private static String authTable = "auth";
    private static String userTable = "user";
    private static String gameTable = "game";

    @BeforeEach
    void setUp() {
        ResetDAO resetDAO = sqlDAO.makeResetDAO();
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
        AuthSqlDAO authSqlDAO = new AuthSqlDAO(authTable);
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
        AuthSqlDAO authSqlDAO = new AuthSqlDAO(authTable);
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
        UserSqlDAO userSqlDAO = new UserSqlDAO(userTable);
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
       UserSqlDAO userSqlDAO = new UserSqlDAO(userTable);
       // Positive
       assertDoesNotThrow(()->{
           userSqlDAO.createUser(userDataType);
           assert userSqlDAO.getUser(name).equals(userDataType);
       });
       assertDoesNotThrow(()->{
           userSqlDAO.createUser(new UserDataType(name, password, null));
       });
       // Negative
       assertThrows(DataAccessException.class,()->{
           userSqlDAO.createUser(new UserDataType(null,null,email));
       });
       assertThrows(DataAccessException.class, ()->{
           userSqlDAO.createUser(new UserDataType(null, password, email));
       });

    }

    /*
        GameSql tests
     */

    @Test
    void gameList() {
    }

    @Test
    void newGame() {
    }

    @Test
    void findGame() {
    }

    @Test
    void updateGameData() {
    }
}