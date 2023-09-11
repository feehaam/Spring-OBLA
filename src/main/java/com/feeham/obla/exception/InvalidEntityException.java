package com.feeham.obla.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidEntityException extends RuntimeException {
    private final String exception = "ValidationException";
    private final String operation;
    private final String message;
    private final Object validation;
    public InvalidEntityException(String entityName, String message, Object validattion) {
        operation = "The entity '" + entityName + "' is invalid";
        this.message = message;
        this.validation = validattion;
    }
}
