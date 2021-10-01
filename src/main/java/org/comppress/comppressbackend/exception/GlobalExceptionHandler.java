package org.comppress.comppressbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        return new ResponseEntity<>(new ApiResponse(null,resourceNotFoundException.getMessage(),404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> invalidRequestException(InvalidRequestException invalidRequestException){
        return new ResponseEntity<>(new ApiResponse(null,invalidRequestException.getMessage(),404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>(new ApiResponse(null,exception.getMessage(),404), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
