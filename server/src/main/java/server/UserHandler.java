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
    private final UserService userService;
    public UserHandler(DAORecord daoRecord){ this.userService = new UserService(daoRecord); }


    public Object register(Request req) throws DataAccessException {
        var registration = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType newUser = new UserDataType(
                registration.username(),
                registration.password(),
                registration.email());
        AuthtokenDataType authData = userService.register(newUser);
        if(authData == null){ throw new Forbidden(); }
        return new Gson().toJson(authData);
    }

    public Object login(Request req) throws DataAccessException {
        var login = new Gson().fromJson(req.body(), UserModel.class);
        UserDataType loginUser = new UserDataType(
                login.username(),
                login.password(),
                "");
        AuthtokenDataType authData = userService.login(loginUser);
        if(authData == null){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of("username", authData.username(), "authToken", authData.authToken()));
    }

    public Object logout(Request req) throws DataAccessException {
        AuthtokenDataType auth = new AuthtokenDataType(
                req.headers("authorization"),
                ""
                );
        if(!userService.logout(auth)){ throw new Unauthorized(); }
        return new Gson().toJson(Map.of());
    }
}
