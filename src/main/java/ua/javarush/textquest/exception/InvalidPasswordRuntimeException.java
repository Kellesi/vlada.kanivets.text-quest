package ua.javarush.textquest.exception;

public class InvalidPasswordRuntimeException extends LoginRuntimeException {
    public InvalidPasswordRuntimeException() {
        super();
    }

    public InvalidPasswordRuntimeException(String message) {
        super(message);
    }
}
