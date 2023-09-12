package com.feeham.obla.repository;

import com.feeham.obla.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationRepository extends JpaRepository<Reserve, Long> {
    @Modifying
    @Query("DELETE FROM Reserve r WHERE r.book.id = :bookId AND r.userId = :userId")
    void deleteByBookIdAndUserId(@Param("bookId") Long bookId, @Param("userId") Long userId);
}
