package org.example.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantServiceException extends RuntimeException {
    public RestaurantServiceException(String message){
        super(message);
    }
}
