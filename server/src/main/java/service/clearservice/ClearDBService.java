package service.clearservice;

import dataaccess.DataAccessException;
import dataaccess.interfaces.ResetDAO;
import service.DAORecord;

public class ClearDBService {
    public void clearDB(DAORecord daoRecord) throws DataAccessException {
        ResetDAO reset = daoRecord.getDatabaseReset();
        reset.run();
    }
}
