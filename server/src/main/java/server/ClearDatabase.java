package server;

import service.clearservice.ClearDBService;

public class ClearDatabase {
    public void reset(){
        ClearDBService reset = new ClearDBService();
        reset.clearDB();
    }
}
