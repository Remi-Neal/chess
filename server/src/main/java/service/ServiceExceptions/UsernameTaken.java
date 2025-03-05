package service.ServiceExceptions;

public class UsernameTaken extends RuntimeException {
    public UsernameTaken() { super("Error: already taken"); }
}
