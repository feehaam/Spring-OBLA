package com.feeham.obla.exception;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(String message, String operation, String reason) {
        super("UserNotFoundException", message, operation, reason);
    }
}