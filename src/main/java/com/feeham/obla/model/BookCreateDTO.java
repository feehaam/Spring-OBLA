package com.feeham.obla.model;

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
    private String description;
}
