package com.feeham.obla.controller;

import com.feeham.obla.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SecurityTestController {
    @GetMapping("/hello")
    public ResponseEntity<?> sayHello(){
        throw new InvalidEntityException("model1", "model2", "message", new HashMap<String, String>());
        //return ResponseEntity.ok("Hello World");
    }
    @GetMapping("/hello2")
    public ResponseEntity<?> sayHello2(){
        throw new NotFoundException("message", "operation", "reason");
        //return ResponseEntity.ok("Hello World");
    }
}
