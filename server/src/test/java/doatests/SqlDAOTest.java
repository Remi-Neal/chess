package doatests;

import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.ResetDAO;
import dataaccess.sql.UserSqlDAO;
import org.eclipse.jetty.io.Connection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SqlDAOTest {

    private static SqlDAO sqlDAO = new SqlDAO();

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
    void makeUserDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }

    @Test
    void makeGameDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }

    @Test
    void makeAuthDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }

    @Test
    void makeResetDAO() {
        UserSqlDAO userSqlDAO = new UserSqlDAO("user");
        assert userSqlDAO != null;
    }
}