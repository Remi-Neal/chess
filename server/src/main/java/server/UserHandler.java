package server;

import com.google.gson.Gson;

import database.datatypes.AuthtokenDataType;
import database.datatypes.UserDataType;
import server.models.UserModel;
import service.ServiceExceptions.Unauthorized;
import service.ServiceExceptions.Forbidden;
import service.userservice.UserService;
import spark.Request;

import java.util.Map;

public class UserHandler {
    final static UserService userService = new UserService();
    //TODO: Add methods to handle major User functions
    // Methods receive GSON data, unpack it, then format it to call service endpoints
    // Finally methods will turn returned data back into GSON and send it back to the server

    //TODO: Add error handling to methods to return the proper err messages

    public static Object register(Request req){
        //TODO: Add Error handling
        var registration = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType newUser = new UserDataType(
                registration.username(),
                registration.password(),
                registration.email());
        AuthtokenDataType authData = userService.register(newUser);
        if(authData == null) throw new Forbidden();
        return new Gson().toJson(authData);
    }

    public static Object login(Request req){
        //TODO: Add Error handling
        var login = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType loginUser = new UserDataType(
                login.username(),
                login.password(),
                "");
        AuthtokenDataType authData = userService.login(loginUser);
        if(authData == null) throw new Unauthorized();
        return new Gson().toJson(Map.of("username", authData.username(), "authToken", authData.authToken()));
    }

    public static Object logout(Request req){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                ""
                );
        if(!userService.logout(auth)) throw new Unauthorized();
        return new Gson().toJson(Map.of());
    }
}
