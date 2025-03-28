package ui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ui.exceptions.ResponseException;
import ui.server_request_records.LoginRequest;
import ui.server_request_records.RegistrationRequest;

import static org.junit.jupiter.api.Assertions.*;

class ServerFacadeTests {

    static ServerFacade serverFacade;

    @BeforeAll
    public static void init(){
        serverFacade = new ServerFacade("http://localhost:8080");
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
}