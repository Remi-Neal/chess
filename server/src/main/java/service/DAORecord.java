package service;

import dataaccess.interfaces.DAO;
import dataaccess.memory.MemoryDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.memory.ResetDataBase;
import dataaccess.interfaces.UserDAO;

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
