package com.feeham.obla.exception;

import com.feeham.obla.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customCommonException(CustomException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        // Determine the response status based on the exception type
        if (exception.getException().contains("Model")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception.getException().contains("Data")) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // Generate and return a custom ErrorResponse with the appropriate status
        return generateResponse(exception, status);
    }

    // Handle InvalidEntityException
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<?> invalidEntity(InvalidEntityException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getException(),
                exception.getMessage(), exception.getOperation(), exception.getValidation(),
                HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    // Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointer(NullPointerException exception) {
        return new ResponseEntity<>(new ErrorResponse("NullPointerException", "Failed to process null value",
                "Null pointer reference", "Null value could not be referenced", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    // Handle generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionParent(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse("Exception", "Failed to perform operation",
                "Uncaught error", "Unknown", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Utility method to generate a custom response
    private ResponseEntity<?> generateResponse(CustomException exception, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(exception.getException(), exception.getMessage(),
                exception.getOperation(), exception.getReason(), status), status);
    }
}