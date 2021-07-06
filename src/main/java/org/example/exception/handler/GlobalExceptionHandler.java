package org.example.exception.handler;

import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.example.exception.RestaurantServiceException;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.response.StatusMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(RestaurantServiceException.class)
    public ResponseEntity<Response<?>> handleRestaurantServiceException(RestaurantServiceException e){
        log.error(e.getMessage());
        return buildResponse(StatusMessage.UNKNOWN_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Response<?>> buildResponse(StatusMessage statusMessage, HttpStatus status){
        var response = Response.builder()
                .meta(ResponseMetadata.builder()
                        .statusMessage(statusMessage.name())
                        .statusCode(status.value())
                        .build())
                        .build();

        return ResponseEntity.status(status)
                .body(response);
    }
}
