package com.feeham.obla.model.reviewdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewCreateDTO {
    private double rating;
    private String comment;
    private Long userId;
    private Long bookId;
}
