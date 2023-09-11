package com.feeham.obla.model.bookdto;
import com.feeham.obla.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class BookReadDTO {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private String status;
}
