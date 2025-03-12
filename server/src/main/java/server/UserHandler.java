package server;

import com.google.gson.Gson;

import memorydatabase.datatypes.AuthtokenDataType;
import memorydatabase.datatypes.UserDataType;
import server.models.UserModel;
import service.exceptions.Unauthorized;
import service.exceptions.Forbidden;
import service.userservice.UserService;
import spark.Request;

import java.util.Map;

public class UserHandler {
    final static UserService USER_SERVICE = new UserService();

    public static Object register(Request req){
        var registration = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType newUser = new UserDataType(
                registration.username(),
                registration.password(),
                registration.email());
        AuthtokenDataType authData = USER_SERVICE.register(newUser);
        if(authData == null){ throw new Forbidden(); }
        return new Gson().toJson(authData);
    }

    public static Object login(Request req){
        var login = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType loginUser = new UserDataType(
                login.username(),
                login.password(),
                "");
        AuthtokenDataType authData = USER_SERVICE.login(loginUser);
        if(authData == null){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of("username", authData.username(), "authToken", authData.authToken()));
    }

    public static Object logout(Request req){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                ""
                );
        if(!USER_SERVICE.logout(auth)){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of());
    }
}
