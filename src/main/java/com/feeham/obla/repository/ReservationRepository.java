package com.feeham.obla.repository;

import com.feeham.obla.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reserve, Long> {
    void deleteByReserveId(long reserveId);
}
