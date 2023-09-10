package com.feeham.obla.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String exception;
    private String operation;
    private String message;
    private Object cause;
    private HttpStatus status;
}
