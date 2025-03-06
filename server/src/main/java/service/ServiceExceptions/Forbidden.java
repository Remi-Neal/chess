package service.ServiceExceptions;

import com.google.gson.Gson;

import java.util.Map;

public class Forbidden extends RuntimeException {
    public Forbidden() { super("Error: already taken"); }

    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage()));
    }
}
