import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.ServerFacade;
import ui.exceptions.ResponseException;
import ui.requestrecords.CreateGameRequest;
import ui.requestrecords.JoinRequest;
import ui.requestrecords.LoginRequest;
import ui.requestrecords.RegistrationRequest;

class ServerFacadeTests {
    // TODO: Create positive and negative tests for all sever facade classes
    static ServerFacade serverFacade;

    final String USER_NAME = "name";
    final String PASSWORD = "name";
    @BeforeAll
    public static void init(){
        serverFacade = new ServerFacade("http://localhost:8080");
    }

    @BeforeEach
    public  void resetDB(){
        try {
            serverFacade.callClearDatabase();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private String createAuth(){
        try {
            var loginRes = serverFacade.callRegistration(new RegistrationRequest(USER_NAME, PASSWORD, "name@name"));
            return loginRes.authToken();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCallLogin(){
        // Test fails because the db is cleared before this test
        try {
            String authToken = createAuth();
            serverFacade.callLogout(authToken);
            var response = serverFacade.callLogin(new LoginRequest(USER_NAME, PASSWORD));
            assert response != null;
            assert response.username() != null;
            assert response.authToken() != null;
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCallRegistration(){
        try{
            var response = serverFacade.callRegistration(new RegistrationRequest("name","name","name@name"));
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCallLogout(){
        String authToken = createAuth();
        try{
            serverFacade.callLogout(authToken);
            System.out.println("Test logout passed");
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateGame(){
        String authToken = createAuth();
        try{
            var response = serverFacade.callCreateGame(authToken, new CreateGameRequest("game"));
            System.out.println(response);
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCistGames(){
        String authToken = createAuth();
        try{
            var response = serverFacade.callListGames(authToken);
            System.out.println(response);
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCoinGame(){
        String authToken = createAuth();
        try{
            var response = serverFacade.callCreateGame(authToken, new CreateGameRequest("game"));
            serverFacade.callJoinGame(authToken, new JoinRequest("Black", response.gameID()));
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCallCreateGame(){

    }
}