package com.feeham.obla.model.borrow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class BorrowCreateDTO {
    private Long borrowId;
    private Date dueDate;
    private Long userId;
    private Long bookId;
}
