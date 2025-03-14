package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SqlDAO;
import dataaccess.interfaces.DAO;
import service.DAORecord;
import service.exceptions.ExceptionHandler;
import spark.*;

import java.util.Map;

public class Server {
    private final DAORecord DAO_RECORD;
    private final ClearDatabase RESET = new ClearDatabase();
    private final UserHandler USER_HANDLER;
    private final GameHandler GAME_HANDLER;
    public Server(){
        DAO_RECORD = new DAORecord(new SqlDAO());
        USER_HANDLER = new UserHandler(DAO_RECORD);
        GAME_HANDLER = new GameHandler(DAO_RECORD);
    }

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

    private Object reset(Request req, Response res) {
        try {
            RESET.reset(DAO_RECORD);
            return new Gson().toJson(Map.of());
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object register(Request req, Response res) {
        try {
            return USER_HANDLER.register(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object login(Request req, Response res) {
        try {
            return USER_HANDLER.login(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object logout(Request req, Response res) {
        try {
            return USER_HANDLER.logout(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object gameList(Request req, Response res) {
        try {
            return GAME_HANDLER.gameList(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object createGame(Request req, Response res) {
        try {
            return GAME_HANDLER.createGame(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            return GAME_HANDLER.joinGame(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }
}
