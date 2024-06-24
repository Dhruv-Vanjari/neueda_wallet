package com.example.wallet.service;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.TransactionType;
import com.example.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WalletServiceImpl implements WalletServiceInterface {

    private final List<Wallet> walletsList = new ArrayList<>();
    private Long transactionCounter = 0L;

    @Override
    public Boolean createWallet(Wallet wallet) {
        walletsList.add(wallet);
        return true;
    }

    @Override
    public void deleteWallet(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        walletsList.remove(wallet);
    }

    @Override
    public Integer getBalance(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        return wallet.getBalance();
    }

    @Override
    public Wallet getWalletById(Long walletId) throws NoSuchElementException {
        return walletsList.stream()
                .filter(wallet -> wallet.getId().equals(walletId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Wallet not found with id " + walletId));
    }

    @Override
    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletsList.stream()
                .filter(wallet -> wallet.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Transaction deposit(Long walletId, Integer amount) {
        Wallet wallet = getWalletById(walletId);
        wallet.setBalance(wallet.getBalance() + amount);
        Transaction transaction = new Transaction(LocalDateTime.now(), TransactionType.DEPOSIT, amount, ++transactionCounter);
        wallet.addTransaction(transaction);
        return transaction;
    }

    @Override
    public Transaction withdraw(Long walletId, Integer amount) {
        Wallet wallet = getWalletById(walletId);
        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance in wallet with id " + walletId);
        }
        wallet.setBalance(wallet.getBalance() - amount);
        Transaction transaction = new Transaction(LocalDateTime.now(),TransactionType.WITHDRAWAL, amount,++transactionCounter);
        wallet.addTransaction(transaction);
        return transaction;
    }
}


//package com.example.wallet.service;
//
//import com.example.wallet.model.Transaction;
//import com.example.wallet.model.Wallet;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class WalletServiceImpl implements WalletServiceInterface {
//
//
//    @Override
//    public Boolean createWallet(Wallet wallet) {
//        return null;
//    }
//
//    @Override
//    public void deleteWallet(Long walletId) {
//
//    }
//
//    @Override
//    public Integer getBalance(Long walletId) {
//        return 0;
//    }
//
//    @Override
//    public Wallet getWalletById(Long walletId) {
//        return null;
//    }
//
//    @Override
//    public List<Wallet> getWalletsByUserId(Long userId) {
//        return List.of();
//    }
//
//    @Override
//    public Transaction deposit(Long walletId, Integer amount) {
//        return null;
//    }
//
//    @Override
//    public Transaction withdraw(Long walletId, Integer amount) {
//        return null;
//    }
//}
