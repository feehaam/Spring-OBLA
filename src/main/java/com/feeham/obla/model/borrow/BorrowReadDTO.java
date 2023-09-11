package com.feeham.obla.model.borrow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowReadDTO {
    private Long borrowId;
    private Long bookId;
    private String bookTitle;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
