package com.example.wallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class Transaction {

    private Long id;
    private Integer amount;

    private TransactionType type;
    private LocalDateTime timestamp;
    private Long userId;


    private Wallet wallet;

    public Transaction(LocalDateTime timestamp, TransactionType type, Integer amount, Long id) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.id = id;
    }




    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }

    public Long getUserId() {
        return userId;
    }

}
