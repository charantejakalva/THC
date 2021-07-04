package org.example.exception;

public class ReservationServiceException extends RuntimeException{
    public ReservationServiceException(String message){
        super(message);
    }
}
