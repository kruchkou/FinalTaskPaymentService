package util.exception;

public class HashException extends Exception{

    public HashException() {
        super();
    }

    public HashException(String message) {
        super(message);
    }

    public HashException(String message, Throwable cause) {
        super(message, cause);
    }

    public HashException(Throwable cause) {
        super(cause);
    }

}
