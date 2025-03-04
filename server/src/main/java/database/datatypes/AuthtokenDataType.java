package database.datatypes;

/**
 * Data class storing Authorization data for Chess Game API
 */
public record AuthtokenDataType(String authToken, String userName) {
    @Override
    public String toString(){
        return "{\n" +
                "\r\t'authToken': " + authToken +
                "\r\t'userName: " + userName +
                "\n}";
    }

    public String getAuthToken() { return this.authToken; }
    public String getUserName(){ return this.userName; }
}
