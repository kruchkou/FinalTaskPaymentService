package by.epamtc.paymentservice.dao.exception;

public class ConnectionPoolInitError extends Error {


    public ConnectionPoolInitError() {
        super();
    }

    public ConnectionPoolInitError(String message) {
        super(message);
    }

    public ConnectionPoolInitError(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolInitError(Throwable cause) {
        super(cause);
    }

}
