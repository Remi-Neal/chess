package service;

import dataaccess.interfaces.*;

public record DAORecord(DAO dao) {
    public UserDAO getUserDAO(){
        return dao.makeUserDAO();
    }
    public GameDAO getGameDAO(){
        return dao.makeGameDAO();
    }
    public AuthDAO getAuthDAO(){
        return dao.makeAuthDAO();
    }
    public ResetDAO getDatabaseReset(){ return dao.makeResetDAO(); }
}
