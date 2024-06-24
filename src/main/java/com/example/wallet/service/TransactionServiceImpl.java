package com.example.wallet.service;

import com.example.wallet.model.Transaction;

import java.util.List;

public class TransactionServiceImpl implements TransactionServiceInterface {
    @Override
    public Transaction transfer(Long fromWalletId, Long toWalletId, Integer amount) {
        return null;
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return List.of();
    }
}
