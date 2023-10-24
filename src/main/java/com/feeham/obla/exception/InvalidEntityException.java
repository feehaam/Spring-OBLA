package com.feeham.obla.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidEntityException extends RuntimeException {
    private final String exception = "ValidationException";
    private final String operation;
    private final String message;
    private final String validation;
    public InvalidEntityException(String entityName, String message, String validation) {
        this.operation = "The entity '" + entityName + "' is invalid";
        this.message = message;
        this.validation = validation;
    }
}
