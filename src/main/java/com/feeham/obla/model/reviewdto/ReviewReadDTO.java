package com.feeham.obla.model.reviewdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewReadDTO {
    private Long reviewId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewTime;
    private String userFullName;
    private String bookTitle;
}
