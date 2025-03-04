package database.datatypes;

public class UserDataType {
    private static String userName;
    private static String password;
    private static String email;

    public UserDataType(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return "{\n" +
                "\r\t'userName': " + userName +
                "\r\t'password': " + password +
                "\r\t'email': " + email +
                "\n}";
    }

    public static String getUserName(){ return userName; }
    public static String getPassword(){ return password; }
    public static String getEmail(){ return email; }
}
