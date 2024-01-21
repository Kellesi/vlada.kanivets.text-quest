package ua.javarush.textquest.exception;

public class UserNotFoundRuntimeException extends LoginRuntimeException {
    public UserNotFoundRuntimeException(){

    }
    public UserNotFoundRuntimeException(String message){
        super(message);
    }
}
