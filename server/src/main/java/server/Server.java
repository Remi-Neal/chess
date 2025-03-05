package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.jshell.spi.ExecutionControl;
import service.ServiceExceptions.ExceptionHandler;
import spark.*;
import server.UserHandler;
import server.GameHandler;

import java.util.Map;

public class Server {
    ClearDatabase reset = new ClearDatabase();
    UserHandler userHandler = new UserHandler();
    GameHandler gameHandler = new GameHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.delete("/db", this::reset);

        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.post("/game", this::createGame);
        Spark.get("/game", this::gameList);
        Spark.put("/game", this::joinGame);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(Exception e, Response res) {
        ExceptionHandler.handleEx(e, res);
    }

    // TODO: Add error handling for the following errors
    /*
        500: {"message": ""}
        400: {"message": "Error: bad request"}
        401: { "message": "Error: unauthorized" }
        403: { "message": "Error: already taken" }
     */
    private Object reset(Request req, Response res) {
        try {
            reset.reset();
            return new Gson().toJson(Map.of("", ""));
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object register(Request req, Response res) {
        try {
            return UserHandler.register(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object login(Request req, Response res) {
        try {
            return UserHandler.login(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object logout(Request req, Response res) {
        try {
            return UserHandler.logout(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object gameList(Request req, Response res) {
        try {
            return GameHandler.gameList(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object createGame(Request req, Response res) {
        try {
            return GameHandler.createGame(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            return GameHandler.joinGame(req, res);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return null;
        }
    }
}
