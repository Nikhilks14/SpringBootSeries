package com.practice.SpringBoot.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError(LocalDateTime timestamp) {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus statusCode) {
        this(LocalDateTime.now());
        this.error = error;
        this.statusCode = statusCode;
    }
}
