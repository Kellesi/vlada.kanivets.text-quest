package ua.javarush.textquest.exception;

public class LoginRuntimeException extends RuntimeException {
    public LoginRuntimeException() {
        super();
    }

    public LoginRuntimeException(String message) {
        super(message);
    }
}
