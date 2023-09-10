package com.feeham.obla.exception;

public class DatabaseException extends CustomException{
    public DatabaseException(String message, String operation, String reason) {
        super("DatabaseException", message, operation, reason);
    }
}