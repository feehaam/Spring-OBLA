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
    private String author;
    private String description;
}
