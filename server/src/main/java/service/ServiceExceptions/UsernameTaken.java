package service.ServiceExceptions;

import com.google.gson.Gson;

import java.util.Map;

public class UsernameTaken extends RuntimeException {
    public UsernameTaken() { super("Error: already taken"); }

    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage()));
    }
}
