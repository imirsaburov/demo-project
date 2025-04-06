package com.example.demo.transaction;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class TransactionDTO {
    private Long id;
    private BigInteger amount;
    private Long userId;
    private TransactionType type;
    private LocalDateTime time;
}
