/**
 * Created by mustafaiman on 25/10/15.
 */
public class InvalidMessageException
        extends RuntimeException {
    public InvalidMessageException() {
        super("Invalid protocol message.");
    }

    public InvalidMessageException(String message) {
        super("Invalid protocol message. " + message);
    }
}
