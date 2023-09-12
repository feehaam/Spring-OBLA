package com.feeham.obla.service.interfaces;

public interface ReservationService {
    public void reserve(Long userId, Long bookId);
    public void cancel(Long userId, Long bookId);
}
