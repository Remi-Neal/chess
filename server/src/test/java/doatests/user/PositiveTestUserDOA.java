package doatests.user;

import dataaccess.DOA;
import dataaccess.resetdata.ResetDataBase;
import dataaccess.userdata.UserDOA;
import database.datatypes.UserDataType;

public class PositiveTestUserDOA {
    final UserDOA userDOA;
    final ResetDataBase resetDB;
    public PositiveTestUserDOA(){
        DOA doa = new DOA();
        userDOA = doa.makeUserDOA();
        resetDB = doa.makeClearer();
    }

    public String testPositiveAddUser(){
        try {
            UserDataType userData = new UserDataType(
                    "Charles",
                    "Dickens",
                    "moregrulesir@hotmail.com");
            userDOA.createUser(userData);
            return "User added successfully!";
        } catch (RuntimeException e){
            return e.toString();
        }
    }
    public String
}
