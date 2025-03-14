package doatests.user;

import dataaccess.MemoryDAO;
import dataaccess.memory.ResetMemoryDAO;
import dataaccess.memory.UserMemoryDAO;
import datatypes.UserDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDOATests {
    final UserMemoryDAO userDOA;
    final ResetMemoryDAO resetDB;
    public UserDOATests(){
        MemoryDAO doa = new MemoryDAO();
        userDOA = (UserMemoryDAO) doa.makeUserDAO();
        resetDB = (ResetMemoryDAO) doa.makeResetDAO();
    }

    @BeforeEach
    public void resetDB(){
        resetDB.run();
    }

    @Test
    public void testPositiveGetandSetSingleUser(){
        UserDataType userData = new UserDataType(
                "Charles",
                "Dickens",
                "moregrulesir@hotmail.com");
        userDOA.createUser(userData);
        UserDataType testReturn = userDOA.getUser("Charles");
        Assertions.assertEquals(userData, testReturn, "Assertion: testPositiveGetandSetSingleUser() failed!");
    }

    @Test
    public void testNegativeGetandSetSingleUser(){
        UserDataType userData = new UserDataType(
                "Charles",
                "Dickens",
                "moregrulesir@hotmail.com");
        userDOA.createUser(userData);
        UserDataType testReturn = userDOA.getUser("Dickens");
        Assertions.assertNotEquals(userData, testReturn, "Assertion: testNegativeGetandSetSingleUser() failed!");
    }
}
