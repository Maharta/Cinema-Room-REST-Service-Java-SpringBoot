package cinema.exceptions;

public class InvalidRefundTokenException extends RuntimeException {
    public InvalidRefundTokenException(String message) {
        super(message);
    }
}
