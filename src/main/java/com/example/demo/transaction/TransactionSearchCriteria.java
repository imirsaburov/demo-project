package com.example.demo.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionSearchCriteria {
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;
}
