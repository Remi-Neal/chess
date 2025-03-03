package database.datatypes;

/**
 * Data class storing Authorization data for Chess Game API
 */
public class AuthtokenDataType {
    private static String authToken;
    private static String userName;
    public AuthtokenDataType(String authToken, String userName){
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() { return authToken; }
    public static String getUserName(){ return userName; }
}
