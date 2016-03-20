package phone.book.exception;

/**
 * Created by КСЮША on 03.03.2016.
 */
public class WrongDestinationException extends RuntimeException{
    public WrongDestinationException(String message) {
        super(message);
    }
}
