package server;

import com.google.gson.Gson;
import dataaccess.SqlDAO;
import service.DAORecord;
import service.exceptions.ExceptionHandler;
import spark.*;

import java.util.Map;

public class Server {
    private final DAORecord daoRecord;
    private final ClearDatabase reset = new ClearDatabase();
    private final UserHandler userHandler;
    private final GameHandler gameHandler;
    private final WSHandler wsHandler;
    public Server(){
        this.daoRecord = new DAORecord(new SqlDAO());
        this.userHandler = new UserHandler(this.daoRecord);
        this.gameHandler = new GameHandler(this.daoRecord);
        this.wsHandler = new WSHandler();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.webSocket("/ws", wsHandler); // Websocket needs to be declared prior to other endpoints

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
            reset.reset(this.daoRecord);
            return new Gson().toJson(Map.of());
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object register(Request req, Response res) {
        try {
            return userHandler.register(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object login(Request req, Response res) {
        try {
            return userHandler.login(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object logout(Request req, Response res) {
        try {
            return userHandler.logout(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object gameList(Request req, Response res) {
        try {
            return gameHandler.gameList(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object createGame(Request req, Response res) {
        try {
            return gameHandler.createGame(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            return gameHandler.joinGame(req);
        } catch (Exception e) {
            exceptionHandler(e, res);
            return res.body();
        }
    }
}
