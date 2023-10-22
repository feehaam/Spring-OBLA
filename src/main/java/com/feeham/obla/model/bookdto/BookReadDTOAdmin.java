package com.feeham.obla.model.bookdto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookReadDTOAdmin {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String imgUrl;
    private String description;
    private String status;
    private String deleted;
}