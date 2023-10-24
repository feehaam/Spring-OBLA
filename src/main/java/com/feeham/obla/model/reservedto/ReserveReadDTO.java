package com.feeham.obla.model.reservedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReserveReadDTO {
    private Long reservationId;
    private LocalDateTime reserveDateTime;
    private Long bookId;
    private String bookTitle;
    private String bookCover;
}
