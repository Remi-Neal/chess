package doatests.auth;

import dataaccess.memory.MemoryDAO;
import dataaccess.memory.AuthMemoryDAO;
import dataaccess.memory.ResetDataBase;
import memorydatabase.datatypes.AuthtokenDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAuthDOA {
    AuthMemoryDAO authDOA;
    ResetDataBase reset;
    public TestAuthDOA(){
        MemoryDAO doa = new MemoryDAO();
        authDOA = (AuthMemoryDAO) doa.makeAuthDAO();
        reset = doa.makeClearer();
    }

    @BeforeEach
    public void reset(){
        reset.run();
    }

    @Test
    public void positiveTestAuthDOA(){
        AuthtokenDataType initial = new AuthtokenDataType("tolkien", "hobbit_lover");
        authDOA.createAuth(initial);
        AuthtokenDataType authReturn = authDOA.getAuth("tolkien");
        Assertions.assertEquals(initial, authReturn, "Positive test failed. Values were not equal.");
        authDOA.removeAuth("tolkien");
        authReturn = authDOA.getAuth("tolkien");
        Assertions.assertNotEquals(initial, authReturn, "Positive Test Failed. Did not delete auth data.");
    }

    @Test
    public void negativeTestAuthDOA(){
        AuthtokenDataType initial = new AuthtokenDataType("tolkien", "hobbit_lover");
        authDOA.createAuth(initial);
        AuthtokenDataType authReturn = authDOA.getAuth("martin");
        Assertions.assertNotEquals(initial, authReturn, "Positive test failed. Values were not equal.");
        authDOA.removeAuth("martin");
        authReturn = authDOA.getAuth("tolkien");
        Assertions.assertEquals(initial, authReturn, "Positive Test Failed. Did not delete auth data.");
    }
}
