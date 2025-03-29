package ui;

import com.google.gson.Gson;
import ui.exceptions.ResponseException;
import ui.server_request_records.CreateGameRequest;
import ui.server_request_records.LoginRequest;
import ui.server_request_records.RegistrationRequest;
import ui.server_responce_record.CreateGameResponse;
import ui.server_responce_record.GameListResponse;
import ui.server_responce_record.LoginResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    // Delete database
    public void callClearDatabase() throws ResponseException{
        String path = "/db";
        makeRequest("DELETE", path, null, null, null);
    }

    // User calls
    public LoginResponse callLogin(LoginRequest request) throws ResponseException {
        String path = "/session";
        return makeRequest("POST", path, null, request, LoginResponse.class);// TODO: Create response record to pass to makeRequest()
    }

    public LoginResponse callRegistration(RegistrationRequest request) throws ResponseException {
        String path = "/user";
        return makeRequest("POST", path, null, request, LoginResponse.class);
    }

    public void callLogout(String authToken) throws ResponseException {
        String path = "/session";
        makeRequest("DELETE", path, authToken, null, null);
    }

    //Game Calls
    public CreateGameResponse callCreateGame(String authToken, CreateGameRequest request) throws ResponseException {
        String path = "/game";
        return makeRequest("POST", path, authToken, request, CreateGameResponse.class);
    }

    public GameListResponse callListGames(String authToken) throws ResponseException{
        String path = "/game";
        return makeRequest("GET", path, authToken, null, GameListResponse.class);
    }

    // TODO: handle calling and error processing to api
    private <T> T makeRequest(String method, String path, String authToken, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeRequest(authToken, request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeRequest(String authToken, Object request, HttpURLConnection http) throws IOException {
        if (authToken != null){
            http.addRequestProperty("Authorization", authToken);
        }
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
        }
        String reqData = new Gson().toJson(request);
        try (OutputStream reqBody = http.getOutputStream()) {
            reqBody.write(reqData.getBytes());
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
