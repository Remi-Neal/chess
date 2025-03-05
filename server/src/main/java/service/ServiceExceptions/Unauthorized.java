package service.ServiceExceptions;

public class Unauthorized extends RuntimeException {
    public Unauthorized() {
        super("Error: unauthorized");
    }
}
