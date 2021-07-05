package org.example.exception;

public class MenuServiceException extends RuntimeException{
    public MenuServiceException(String message){
        super(message);
    }
}
