package com.example.demo.transaction;

import com.example.demo.common.utils.RandomTimeGenerator;
import com.example.demo.user.UserService;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final EnumMap<TransactionType, BiConsumer<Long, BigInteger>> userBalanceChangeMethodMap = new EnumMap<>(TransactionType.class);


    private final TransactionRepository transactionRepository;
    private final UserService userService;


    @PostConstruct
    public void init() {
        userBalanceChangeMethodMap.put(TransactionType.DEBIT, userService::debitBalance);
        userBalanceChangeMethodMap.put(TransactionType.CREDIT, userService::creditBalance);
    }

    @Transactional
    public TransactionDTO create(CreateTransactionRequest createTransactionRequest) {
        userBalanceChangeMethodMap
                .get(createTransactionRequest.type())
                .accept(createTransactionRequest.userId(), createTransactionRequest.amount());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(createTransactionRequest.userId());
        transactionEntity.setAmount(createTransactionRequest.amount());
        transactionEntity.setType(createTransactionRequest.type());
        transactionEntity.setTime(LocalDateTime.now());

        transactionRepository.save(transactionEntity);

        return toDTO(transactionEntity);
    }

    @Transactional
    public TransactionDTO create(CreateTransactionRequest createTransactionRequest, LocalDateTime time) {
        userBalanceChangeMethodMap
                .get(createTransactionRequest.type())
                .accept(createTransactionRequest.userId(), createTransactionRequest.amount());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(createTransactionRequest.userId());
        transactionEntity.setAmount(createTransactionRequest.amount());
        transactionEntity.setType(createTransactionRequest.type());
        transactionEntity.setTime(time);

        transactionRepository.save(transactionEntity);

        return toDTO(transactionEntity);
    }

    public PagedModel<TransactionDTO> findAll(Pageable pageable, TransactionSearchCriteria searchCriteria) {
        Specification<TransactionEntity> specification = toSpecification(searchCriteria);
        return new PagedModel<>(
                transactionRepository.findAll(specification, pageable)
                        .map(this::toDTO)
        );
    }


    private TransactionDTO toDTO(TransactionEntity entity) {
        return TransactionDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .userId(entity.getUserId())
                .time(entity.getTime())
                .build();
    }

    private Specification<TransactionEntity> toSpecification(TransactionSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (Objects.nonNull(searchCriteria.getUserId()))
                predicates.add(criteriaBuilder.equal(root.get(TransactionEntity.Fields.userId), searchCriteria.getUserId()));

            if (Objects.nonNull(searchCriteria.getFrom()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(TransactionEntity.Fields.time), LocalDateTime.of(searchCriteria.getFrom(), LocalTime.MIN)));

            if (Objects.nonNull(searchCriteria.getTo()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(TransactionEntity.Fields.time), LocalDateTime.of(searchCriteria.getTo(), LocalTime.MAX)));

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }



    public Long countTransactions() {
        return transactionRepository.count();
    }
}
