package com.feeham.obla.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class BorrowReadDTO {
    private Long borrowId;
    private Date dueDate;
    private Long userId;
    private Long bookId;
    private String userFullName;
    private String bookTitle;
}
