package service;

import dataaccess.DAO;
import dataaccess.MemoryDAO;
import dataaccess.authdata.AuthDAO;
import dataaccess.gamedata.GameDAO;
import dataaccess.resetdata.ResetDataBase;
import dataaccess.userdata.UserDAO;

public record DAORecord() {
    private static DAO dao;
    public DAORecord(){
        dao = new MemoryDAO();
    }
    public UserDAO getUserDAO(){
        return dao.makeUserDAO();
    }
    public GameDAO getGameDAO(){
        return dao.makeGameDAO();
    }
    public AuthDAO getAuthDAO(){
        return dao.makeAuthDAO();
    }
    public ResetDataBase getDatabaseReset(){
        MemoryDAO memDAO = (MemoryDAO) dao;
        return memDAO.makeClearer();
    }
}
