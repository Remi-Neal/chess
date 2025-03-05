package server;

import com.google.gson.Gson;

import database.datatypes.AuthtokenDataType;
import database.datatypes.UserDataType;
import server.models.UserModel;
import service.ServiceExceptions.UsernameTaken;
import service.userservice.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class UserHandler {
    final static UserService userService = new UserService();
    //TODO: Add methods to handle major User functions
    // Methods receive GSON data, unpack it, then format it to call service endpoints
    // Finally methods will turn returned data back into GSON and send it back to the server

    //TODO: Add error handling to methods to return the proper err messages

    public static Object register(Request req, Response res){
        //TODO: Add Error handling
        var registration = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType newUser = new UserDataType(
                registration.username(),
                registration.password(),
                registration.email());
        AuthtokenDataType authData = userService.register(newUser);
        if(authData == null) throw new UsernameTaken();
        return new Gson().toJson(authData);
    }

    public static Object login(Request req, Response res){
        //TODO: Add Error handling
        var login = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType loginUser = new UserDataType(
                login.username(),
                login.password(),
                "");
        AuthtokenDataType authData = userService.login(loginUser);
        return new Gson().toJson(authData);
    }

    public static Object logout(Request req, Response res){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                ""
                );
        if(userService.logout(auth)){
            return new Gson().toJson(null);
        } else {
            res.status(401);
            return new Gson().toJson(Map.of("message", "Error: Unauthorized"));
        }
    }
}
