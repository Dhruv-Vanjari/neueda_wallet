package com.example.wallet.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    private List<Wallet> wallets = new ArrayList<>();

    // Getters and Setters

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
    }

    public void removeWallet(Wallet wallet) {
        wallets.remove(wallet);
    }
    public List<Wallet> getWallets() {
        return wallets;
    }
}
