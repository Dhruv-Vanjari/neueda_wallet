package com.example.wallet.service;

import com.example.wallet.model.Transaction;

import java.util.List;

public interface TransactionServiceInterface {

    Transaction transfer(Long fromWalletId, Long toWalletId, Integer amount);

    Transaction getTransactionById(Long transactionId);
    List<Transaction> getTransactionsByWalletId(Long walletId);
    List<Transaction> getTransactionsByUserId(Long userId);


}
