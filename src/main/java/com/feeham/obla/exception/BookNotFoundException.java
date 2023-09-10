package com.feeham.obla.exception;

public class BookNotFoundException extends CustomException{
    public BookNotFoundException(String message, String operation, String reason) {
        super("BookNotFoundException", message, operation, reason);
    }
}