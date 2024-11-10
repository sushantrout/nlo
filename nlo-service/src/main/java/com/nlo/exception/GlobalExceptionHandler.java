package com.nlo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle any other exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {
        logger.error("An error occurred: ", ex);
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Throwable - for catching low-level errors and more critical issues
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleThrowable(Throwable ex, WebRequest request) {
        // Log the complete stack trace for debugging
        logger.error("Critical error occurred: ", ex);
        return new ResponseEntity<>("A critical error occurred. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
