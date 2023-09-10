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
    public InvalidEntityException(String model1, String model2, String message, Object validattion) {
        operation = "Conversion between "+model1+" & "+model2;
        this.message = message;
        this.validation = validattion;
    }
}
