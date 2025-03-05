package server;

import service.clearservice.ClearDBService;

public class ClearDatabase {
    // TODO: Create handler to easily clear the database
    public void reset(){
        ClearDBService reset = new ClearDBService();
        reset.clearDB();
    }
}
