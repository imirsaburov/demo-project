package com.example.demo.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record CreateTransactionRequest(
        @NotNull @Min(10) BigInteger amount,
        @NotNull Long userId,
        @NotNull TransactionType type
) {
}
