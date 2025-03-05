package service.ServiceExceptions;

import com.google.gson.Gson;

import java.util.Map;

public class Unauthorized extends RuntimeException {
    public Unauthorized() {
        super("Error: unauthorized");
    }

    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage()));
    }
}
