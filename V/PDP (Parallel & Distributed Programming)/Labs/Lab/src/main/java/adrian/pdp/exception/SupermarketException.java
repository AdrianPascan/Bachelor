package adrian.pdp.exception;

public class SupermarketException extends RuntimeException {
    public SupermarketException(String message) {
        super(message);
    }

    public SupermarketException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupermarketException(Throwable cause) {
        super(cause);
    }
}
