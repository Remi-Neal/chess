package service.ServiceExceptions;

import com.google.gson.Gson;
import spark.Response;


public class ExceptionHandler {
    public static void handleEx(Exception e, Response res){
        switch (e) {
            case BadRequest badRequest -> {
                res.status(400);
                res.body(new Gson().toJson(e));
            }
            case Unauthorized unauthorized -> {
                res.status(401);
                res.body(new Gson().toJson(e));
            }
            case UsernameTaken usernameTaken -> {
                res.status(403);
                res.body(new Gson().toJson(e));
            }
            case null, default -> {
                res.status(500);
                res.body(new Gson().toJson(e));
            }
        }
    }
}
