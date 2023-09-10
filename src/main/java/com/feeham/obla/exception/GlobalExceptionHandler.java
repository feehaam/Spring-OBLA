package com.feeham.obla.exception;

import com.feeham.obla.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customCommonException(CustomException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(exception.getException().contains("Model")) status = HttpStatus.BAD_REQUEST;
        else if(exception.getException().contains("Data")) status = HttpStatus.INTERNAL_SERVER_ERROR;
        return generateResponse(exception, status);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<?> invalidEntity(InvalidEntityException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getException(),
                exception.getMessage(), exception.getOperation(), exception.getValidation(),
                HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointer(NullPointerException exception) {
        return new ResponseEntity<>(new ErrorResponse("NullPointerException", "Failed to process null value",
                "Null pointer reference", "Null value could not be referenced", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionParent(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse("Exception", "Failed to perform operation",
                "Uncaught error", "Unknown", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> generateResponse(CustomException exception, HttpStatus status){
        return new ResponseEntity<>(new ErrorResponse(exception.getException(), exception.getMessage(),
                exception.getOperation(), exception.getReason(), status), status);
    }
}
