package com.feeham.obla.exception;

public class NotFoundException extends CustomException{
    public NotFoundException(String message, String operation, String reason) {
        super("NotFoundException", message, operation, reason);
    }
}