package com.locngo.loansystem.errorhandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;

    private HttpStatus httpStatus;

    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, Throwable exception) {
        this();
        this.httpStatus = httpStatus;
        this.message = exception.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
