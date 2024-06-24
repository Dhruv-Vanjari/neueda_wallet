package com.example.wallet.model;

import com.example.wallet.model.User;
import com.example.wallet.model.TransactionType;
import com.example.wallet.service.UserServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private Long id;
    private String name;
    private Integer balance;
    private String currency;


    private Long userId;







    private final List<Transaction> transactions = new ArrayList<>();

    // Getters and Setters

    public Wallet(String name, Long id, String currency, Long userId) {
        this.name = name;
        this.id = id;
        this.currency = currency;
        this.userId = userId;

        this.balance = 0;
    }





    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }



    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeWallet(Transaction transaction) {
        transactions.remove(transaction);
    }


}
