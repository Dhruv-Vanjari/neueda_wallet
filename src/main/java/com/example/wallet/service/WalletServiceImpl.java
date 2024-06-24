package com.example.wallet.service;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.Wallet;

import java.util.List;

public class WalletServiceImpl implements WalletServiceInterface {


    @Override
    public Boolean createWallet(Wallet wallet) {
        return null;
    }

    @Override
    public void deleteWallet(Long walletId) {

    }

    @Override
    public Integer getBalance(Long walletId) {
        return 0;
    }

    @Override
    public Wallet getWalletById(Long walletId) {
        return null;
    }

    @Override
    public List<Wallet> getWalletsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Transaction deposit(Long walletId, Integer amount) {
        return null;
    }

    @Override
    public Transaction withdraw(Long walletId, Integer amount) {
        return null;
    }
}
