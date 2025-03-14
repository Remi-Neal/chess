package server;

import dataaccess.DataAccessException;
import service.DAORecord;
import service.clearservice.ClearDBService;

public class ClearDatabase {
    public void reset(DAORecord daoRecord) throws DataAccessException {
        ClearDBService reset = new ClearDBService();
        reset.clearDB(daoRecord);
    }
}
