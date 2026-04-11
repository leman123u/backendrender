package personalbudget.exception;

/**
 * Invalid or expired password-reset token (expected client error, not a server bug).
 */
public class ResetTokenException extends RuntimeException {

    public ResetTokenException(String message) {
        super(message);
    }
}

