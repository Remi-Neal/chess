package service.clearservice;

import dataaccess.resetdata.ResetDataBase;
import service.ServiceInterface;

public class ClearDBService {
    public void clearDB(){
        ResetDataBase reset = ServiceInterface.daoRecord.getDatabaseReset();
        reset.run();
    }
}
