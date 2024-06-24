package com.example.wallet.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private Long id;
    private String name;
    private Integer balance;
    private String currency;


    private User user;

    private List<Transaction> transactions = new ArrayList<>();

    // Getters and Setters

    public Wallet(String name, Long id, String currency, User user) {
        this.name = name;
        this.id = id;
        this.currency = currency;
        this.user = user;

        this.balance = 0;
    }

    public String getName() {
        return name;
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
