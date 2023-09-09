package com.feeham.obla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewReadDTO {
    private Long reviewId;
    private double rating;
    private String comment;
    private String userFullName;
    private String bookTitle;
}
