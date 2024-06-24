package com.example.wallet.service;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.TransactionType;
import com.example.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionServiceInterface {

    @Autowired
    WalletServiceImpl walletService;

    @Override
    public Transaction transfer(Long fromWalletId, Long toWalletId, Integer amount) {
        // Assuming a simple transfer implementation where we withdraw from one wallet and deposit into another
        Transaction withdrawalTransaction = withdraw(fromWalletId, amount);
        Transaction depositTransaction = deposit(toWalletId, amount);

        // You can return either transaction here based on your needs, or create a new transaction object specifically for the transfer
        return withdrawalTransaction;
    }

    private Transaction withdraw(Long walletId, Integer amount) {
        // Implement logic to withdraw amount from wallet
        // For simplicity, returning a dummy transaction here
        return walletService.withdraw(walletId, amount);
    }

    public Transaction deposit(Long walletId, Integer amount) {
        // Implement logic to deposit amount to wallet
        // For simplicity, returning a dummy transaction here
        return walletService.deposit(walletId, amount);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        // Implement logic to fetch transaction by its ID
        // For simplicity, returning null as placeholder
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        return wallet.getTransactions();

    }


    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        // Implement logic to fetch transactions associated with a specific user ID
        // For simplicity, returning an empty list as placeholder
        return List.of();
    }
}


//package com.example.wallet.service;
//
//import com.example.wallet.model.Transaction;
//
//import java.util.List;
//
//public class TransactionServiceImpl implements TransactionServiceInterface {
//    @Override
//    public Transaction transfer(Long fromWalletId, Long toWalletId, Integer amount) {
//        return null;
//    }
//
//    @Override
//    public Transaction getTransactionById(Long transactionId) {
//        return null;
//    }
//
//    @Override
////    public List<Transaction> getTransactionsByWalletId(Long walletId) {
////        return List.of();
////    }
//    public List<Transaction> getTransactionsByWalletId(Long walletId) {
//        return null;
//    }
//
//    @Override
//    public List<Transaction> getTransactionsByUserId(Long userId) {
//        return List.of();
//    }
//}
