package database.datamodels;

/**
 * Data class storing Authorization data for Chess Game API
 */
public class AuthtokenData{
    private static String authToken;
    private static String userName;
    public AuthtokenData(String authToken, String userName){
        this.authToken = authToken;
        this.userName = userName;
    }

    public static String getAuthToken() { return authToken; }
    public static String getUserName(){ return userName; }
}
