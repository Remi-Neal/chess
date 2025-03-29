package ui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.discovery.predicates.IsNestedTestClass;
import ui.exceptions.ResponseException;
import ui.server_request_records.CreateGameRequest;
import ui.server_request_records.LoginRequest;
import ui.server_request_records.RegistrationRequest;

import static org.junit.jupiter.api.Assertions.*;

class ServerFacadeTests {

    static ServerFacade serverFacade;

    @BeforeAll
    public static void init(){
        serverFacade = new ServerFacade("http://localhost:8080");
    }

    private String createAuth(){
        try{
            var loginRes = serverFacade.callRegistration(new RegistrationRequest("name", "name", "name@name"));
            return loginRes.authToken();
        } catch (Exception e){
            try {
                var loginRes = serverFacade.callLogin(new LoginRequest("name", "name"));
                return loginRes.authToken();
            } catch (Exception e2){
                throw new RuntimeException(e2);
            }
        }
    }

    @Test
    public void test_callLogin(){
        try {
            var response = serverFacade.callLogin(new LoginRequest("name", "name"));
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_callRegistration(){
        try{
            var response = serverFacade.callRegistration(new RegistrationRequest("name","name","name@name"));
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_callLogout(){
        String authToken = createAuth();
        try{
            serverFacade.callLogout(authToken);
            System.out.println("Test logout passed");
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_createGame(){
        String authToken = createAuth();
        try{
            var response = serverFacade.callCreateGame(authToken, new CreateGameRequest("game"));
            System.out.println(response);
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }
}