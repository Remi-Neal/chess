package server;

import com.google.gson.Gson;

import dataaccess.DataAccessException;
import datatypes.AuthtokenDataType;
import datatypes.UserDataType;
import server.models.UserModel;
import service.DAORecord;
import service.exceptions.Unauthorized;
import service.exceptions.Forbidden;
import service.userservice.UserService;
import spark.Request;

import java.util.Map;

public class UserHandler {
    private final UserService USER_SERVICE;
    public UserHandler(DAORecord daoRecord){ USER_SERVICE = new UserService(daoRecord); }


    public Object register(Request req) throws DataAccessException {
        var registration = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType newUser = new UserDataType(
                registration.username(),
                registration.password(),
                registration.email());
        AuthtokenDataType authData = USER_SERVICE.register(newUser);
        if(authData == null){ throw new Forbidden(); }
        return new Gson().toJson(authData);
    }

    public Object login(Request req){
        var login = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType loginUser = new UserDataType(
                login.username(),
                login.password(),
                "");
        AuthtokenDataType authData = USER_SERVICE.login(loginUser);
        if(authData == null){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of("username", authData.username(), "authToken", authData.authToken()));
    }

    public Object logout(Request req){
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                ""
                );
        if(!USER_SERVICE.logout(auth)){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of());
    }
}
