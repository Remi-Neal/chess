package service.clearservice;

import dataaccess.resetdata.ResetDataBase;
import service.ServiceInterface;

public class ClearDBService {
    public void clearDB(){
        ResetDataBase reset = ServiceInterface.DAO_RECORD.getDatabaseReset();
        reset.run();
    }
}
