package com.feeham.obla.controller;

import com.feeham.obla.service.interfaces.ReservationService;
import com.feeham.obla.service.interfaces.UserCredentialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final UserCredentialsService credentials;

    public ReservationController(ReservationService reservationService, UserCredentialsService credentials) {
        this.reservationService = reservationService;
        this.credentials = credentials;
    }

    /**
     * Reserve a book by specifying the book ID.
     *
     * @param bookId The ID of the book to reserve.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/{bookId}/reserve")
    public ResponseEntity<?> reserveBook(@PathVariable Long bookId) {
        reservationService.reserve(credentials.getUserId(), bookId);
        return ResponseEntity.ok("Book successfully reserved.");
    }

    /**
     * Cancel the reservation of a book by specifying the book ID.
     *
     * @param bookId The ID of the book to cancel the reservation for.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/{bookId}/cancel-reservation")
    public ResponseEntity<?> cancelBookReservation(@PathVariable Long bookId) {
        reservationService.cancel(credentials.getUserId(), bookId);
        return ResponseEntity.ok("Book reservation cancelled.");
    }
}
