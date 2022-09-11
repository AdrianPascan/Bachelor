package ro.ubb.socket.common;

public class CommonServiceException extends RuntimeException {
    public CommonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public CommonServiceException(Throwable cause) {
        super(cause);
    }
}
