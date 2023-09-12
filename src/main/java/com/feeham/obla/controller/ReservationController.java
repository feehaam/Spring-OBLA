package com.feeham.obla.controller;

import com.feeham.obla.service.interfaces.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/books/{bookId}/reserve")
    public ResponseEntity<?> reserveBook(@PathVariable Long bookId){
        reservationService.reserve(2L, bookId);
        return ResponseEntity.ok("Book successfully reserved.");
    }

    @PostMapping("/books/{bookId}/cancel-reservation")
    public ResponseEntity<?> cancelBookReservation(@PathVariable Long bookId){
        reservationService.cancel(2L, bookId);
        return ResponseEntity.ok("Book reservation cancelled.");
    }
}
