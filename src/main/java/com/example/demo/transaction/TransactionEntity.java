package com.example.demo.transaction;

import com.example.demo.common.BaseEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class TransactionEntity extends BaseEntity {

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "amount", nullable = false)
    private BigInteger amount;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
