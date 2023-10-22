package com.feeham.obla.model.bookdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookCreateDTO {
    private String title;
    private String author;
    private String isbn;
    private String imgUrl;
    private String description;
}
