package com.feeham.obla.model.reviewdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewUpdateDTO {
    private Long reviewId;
    private double rating;
    private String comment;
}
