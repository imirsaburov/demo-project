package com.example.demo.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionDTO create(@Valid @RequestBody CreateTransactionRequest createTransactionRequest) {
        return transactionService.create(createTransactionRequest);
    }

    @GetMapping("pageable")
    public PagedModel<TransactionDTO> pageable(Pageable pageable, TransactionSearchCriteria searchCriteria) {
        return transactionService.findAll(pageable, searchCriteria);
    }
}
