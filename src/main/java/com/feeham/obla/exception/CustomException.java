package com.feeham.obla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final String exception;
    private final String operation;
    private final String message;
    private final String reason;
}