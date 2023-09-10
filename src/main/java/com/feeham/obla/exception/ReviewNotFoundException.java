package com.feeham.obla.exception;

public class ReviewNotFoundException extends CustomException{
    public ReviewNotFoundException(String message, String operation, String reason) {
        super("ReviewNotFoundException", message, operation, reason);
    }
}