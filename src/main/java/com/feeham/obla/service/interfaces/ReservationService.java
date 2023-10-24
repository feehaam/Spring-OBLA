package com.feeham.obla.service.interfaces;

import com.feeham.obla.model.reservedto.ReserveReadDTO;

import java.util.List;

public interface ReservationService {
    public void reserve(Long userId, Long bookId);
    public void cancel(Long userId, Long bookId);
    public List<ReserveReadDTO> getByUser(Long userId);
}
