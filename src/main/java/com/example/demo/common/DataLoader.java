package com.example.demo.common;

import com.example.demo.transaction.TransactionMockService;
import com.example.demo.user.UserMockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserMockService userMockService;
    private final TransactionMockService transactionMockService;

    @Override
    public void run(String... args) {
        userMockService.loadMockUsers();
        transactionMockService.loadMockTransactions();
    }
}
