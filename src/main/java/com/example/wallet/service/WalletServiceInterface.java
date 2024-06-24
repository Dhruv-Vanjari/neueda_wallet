package com.example.wallet.service;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.Wallet;

import java.util.List;

public interface WalletServiceInterface {

    Boolean createWallet(Wallet wallet);
    void deleteWallet(Long walletId);

    Integer getBalance(Long walletId);

    Wallet getWalletById(Long walletId);
    List<Wallet> getWalletsByUserId(Long userId);

    Transaction deposit(Long walletId, Integer amount);
    Transaction withdraw(Long walletId, Integer amount);



}
