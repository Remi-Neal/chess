package server;

import service.DAORecord;
import service.clearservice.ClearDBService;

public class ClearDatabase {
    public void reset(DAORecord daoRecord){
        ClearDBService reset = new ClearDBService();
        reset.clearDB(daoRecord);
    }
}
