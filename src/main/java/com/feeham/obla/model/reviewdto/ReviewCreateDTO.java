package com.feeham.obla.model.reviewdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewCreateDTO {
    private Integer rating;
    private String comment;
}
