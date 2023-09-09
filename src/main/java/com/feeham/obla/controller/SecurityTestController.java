package com.feeham.obla.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {
    @GetMapping("/hello")
    public ResponseEntity<?> sayHello(){
        return ResponseEntity.ok("Hello World");
    }
}
