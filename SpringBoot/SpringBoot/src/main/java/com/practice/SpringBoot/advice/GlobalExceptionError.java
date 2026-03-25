package com.practice.SpringBoot.advice;

import com.practice.SpringBoot.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionError {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(ResourceNotFoundException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage() , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
