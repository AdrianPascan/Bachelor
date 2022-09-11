package ro.ubb.server.tcp;

public class TcpException extends RuntimeException {
    public TcpException(String message, Throwable cause) {
        super(message, cause);
    }

    public TcpException(Throwable cause) {
        super(cause);
    }

    public TcpException(String message) {
        super(message);
    }
}
