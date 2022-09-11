package flcd.lab2.scanner;

public class LexicalError extends RuntimeException {
    LexicalError(String message) {
        super(message);
    }
}
