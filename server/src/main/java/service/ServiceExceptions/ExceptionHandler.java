package service.ServiceExceptions;

import com.google.gson.Gson;
import spark.Response;

import java.util.Map;


public class ExceptionHandler {
    public static void handleEx(Exception e, Response res){
        switch (e) {
            case BadRequest ignored -> {
                res.status(400);
                BadRequest err = (BadRequest) e;
                res.body(err.toJson());
            }
            case Unauthorized ignored -> {
                res.status(401);
                Unauthorized err = (Unauthorized) e;
                res.body(err.toJson());
            }
            case UsernameTaken ignored -> {
                res.status(403);
                UsernameTaken err = (UsernameTaken) e;
                res.body(err.toJson());
            }
            case null, default -> {
                res.status(500);
                assert e != null;
                String errMessage = e.getMessage();
                res.body(new Gson().toJson(Map.of("message:", "Error: " + errMessage)));
            }
        }
    }
}
