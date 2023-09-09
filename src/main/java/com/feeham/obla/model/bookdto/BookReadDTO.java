package com.feeham.obla.model.bookdto;
import com.feeham.obla.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class BookReadDTO {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private Boolean availability;
    private List<Review> reviews;
}
