package com.example.demo.transaction;

import com.example.demo.common.utils.RandomTimeGenerator;
import com.example.demo.user.UserService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionMockService {

    private static final BigInteger DEFAULT_USER_CREDIT_AMOUNT = BigInteger.valueOf(1_000_000_00);
    private static final LocalDateTime DEFAULT_CREDIT_TIME = LocalDateTime.of(2025, 1, 1, 1, 1, 1);
    private static final Integer DEFAULT_USER_DEBIT_MIN = 100_00;
    private static final Integer DEFAULT_USER_DEBIT_MAX = 10_000_00;
    private static final Integer DEFAULT_TRANSACTION_COUNT = 100;

    private final TransactionService transactionService;
    private final UserService userService;

    @Transactional
    public void loadMockTransactions() {

        if (transactionService.countTransactions() > 0)
            return;

        Faker faker = new Faker();

        userService.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent().forEach(user -> {


            CreateTransactionRequest createTransactionCreditRequest = new CreateTransactionRequest(DEFAULT_USER_CREDIT_AMOUNT, user.getId(), TransactionType.CREDIT);
            transactionService.create(createTransactionCreditRequest, DEFAULT_CREDIT_TIME);

            for (int i = 0; i < DEFAULT_TRANSACTION_COUNT; i++) {

                LocalDateTime randomDateTime = RandomTimeGenerator.getRandomDateTime(DEFAULT_CREDIT_TIME, LocalDateTime.now());
                BigInteger debitAmount = BigInteger.valueOf(faker.number().numberBetween(DEFAULT_USER_DEBIT_MIN, DEFAULT_USER_DEBIT_MAX));
                CreateTransactionRequest createTransactionDebitRequest = new CreateTransactionRequest(debitAmount, user.getId(), TransactionType.DEBIT);
                transactionService.create(createTransactionDebitRequest, randomDateTime);
            }
        });
    }
}
