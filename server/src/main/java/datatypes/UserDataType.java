package datatypes;

public record UserDataType(String userName, String password, String email) {
    @Override
    public String toString(){
        return "{\n" +
                "\r\t'userName': " + userName +
                "\r\t'password': " + password +
                "\r\t'email': " + email +
                "\n}";
    }

    public String getUserName(){ return this.userName; }
    public String getPassword(){ return this.password; }
    public  String getEmail(){ return this.email; }
}
