package com.feeham.obla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class BorrowUpdateDTO {
    private Long borrowId;
    private Date returnDate;
}
