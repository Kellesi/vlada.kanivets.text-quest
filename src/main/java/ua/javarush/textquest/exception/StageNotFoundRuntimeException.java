package ua.javarush.textquest.exception;

public class StageNotFoundRuntimeException extends RuntimeException {
    public StageNotFoundRuntimeException() {
        super();
    }

    public StageNotFoundRuntimeException(String message) {
        super(message);
    }
}
