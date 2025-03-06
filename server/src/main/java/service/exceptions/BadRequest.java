package service.exceptions;

import com.google.gson.Gson;

import java.util.Map;

public class BadRequest extends RuntimeException {
    public BadRequest() {super("Error: bad request");}

    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage()));
    }
}
