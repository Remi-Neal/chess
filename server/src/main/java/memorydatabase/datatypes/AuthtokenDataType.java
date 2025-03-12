package memorydatabase.datatypes;

/**
 * Data class storing Authorization data for Chess GameHandler API
 */
public record AuthtokenDataType(String authToken, String username) {
    @Override
    public String toString(){
        return "{\n" +
                "\r\t'authToken': " + authToken +
                "\r\t'userName: " + username +
                "\n}";
    }
}
