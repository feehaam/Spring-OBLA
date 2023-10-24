package com.feeham.obla.model.bookdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookUpdateDTO {
    private Long bookId;
    private String title;
    private String isbn;
    private String author;
    private String imgUrl;
    private String description;
}
