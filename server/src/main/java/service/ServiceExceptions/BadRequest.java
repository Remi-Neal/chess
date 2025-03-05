package service.ServiceExceptions;

public class BadRequest extends RuntimeException {
    public BadRequest() {
        super("Error: bad request");
    }
}
