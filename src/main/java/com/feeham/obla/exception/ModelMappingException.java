package com.feeham.obla.exception;

public class ModelMappingException extends CustomException {
    public ModelMappingException(String message, String operation, String reason) {
        super("ModelMappingException", message, operation, reason);
    }
}
