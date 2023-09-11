package com.feeham.obla.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {
    @PostMapping("/books/{bookId}/reserve")
    public ResponseEntity<?> reserveBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @PostMapping("/books/{bookId}/cancel-reservation")
    public ResponseEntity<?> cancelBookReservation(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }
}
