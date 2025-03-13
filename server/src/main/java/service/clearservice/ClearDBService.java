package service.clearservice;

import dataaccess.memory.ResetDataBase;
import service.DAORecord;

public class ClearDBService {
    public void clearDB(DAORecord daoRecord){
        ResetDataBase reset = daoRecord.getDatabaseReset();
        reset.run();
    }
}
