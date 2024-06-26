package com.example.wallet.controller;

import com.example.wallet.model.Transaction;
import com.example.wallet.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam Long fromWalletId, @RequestParam Long toWalletId, @RequestParam Integer amount) {
        return transactionService.transfer(fromWalletId, toWalletId, amount);
    }

    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam Long walletId, @RequestParam Integer amount) {
        return transactionService.deposit(walletId, amount);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/wallet/{walletId}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable Long walletId) {
        return transactionService.getTransactionsByWalletId(walletId);
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }
}
