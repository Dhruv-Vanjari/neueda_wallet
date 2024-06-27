package com.example.wallet.controller;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletServiceImpl walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        Wallet createdWallet = walletService.createWallet(wallet);
        return ResponseEntity.ok(createdWallet);
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId) {
        try {
            walletService.deleteWallet(walletId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Integer> getBalance(@PathVariable Long walletId) {
        try {
            Integer balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(balance);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long walletId) {
        try {
            Wallet wallet = walletService.getWalletById(walletId);
            return ResponseEntity.ok(wallet);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wallet>> getWalletsByUserId(@PathVariable Long userId) {
        List<Wallet> wallets = walletService.getWalletsByUserId(userId);
        return ResponseEntity.ok(wallets);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable Long walletId, @RequestBody Integer amount) {
        try {
            Transaction transaction = walletService.deposit(walletId, amount);
            return ResponseEntity.ok(transaction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<Transaction> withdraw(@PathVariable Long walletId, @RequestBody Integer amount) {
        try {
            Transaction transaction = walletService.withdraw(walletId, amount);
            return ResponseEntity.ok(transaction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}