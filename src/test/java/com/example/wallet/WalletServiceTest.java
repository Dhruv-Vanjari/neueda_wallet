package com.example.wallet;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.Wallet;
import com.example.wallet.model.TransactionType;
import com.example.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletServiceTest {

    @Autowired
    WalletServiceImpl walletService;

    @Test
    public void testCreateWallet() {
        Wallet wallet = new Wallet("citi1", 1L, "INR", 123L);
        assertThat(walletService.createWallet(wallet)).isEqualTo(true);
    }

    @Test
    public void testDeleteWallet() {
        Wallet wallet = new Wallet("citi2", 2L, "INR", 123L);
        walletService.createWallet(wallet);
        walletService.deleteWallet(2L);
        assertThrows(NoSuchElementException.class, () -> walletService.getWalletById(2L));
    }

    @Test
    public void testGetBalance() {
        Wallet wallet = new Wallet("citi3", 3L, "INR", 123L);
        wallet.setBalance(500);
        walletService.createWallet(wallet);
        assertThat(walletService.getBalance(3L)).isEqualTo(500);
    }

    @Test
    public void testGetWalletById() {
        Wallet wallet = new Wallet("Tanaya", 4L, "INR", 123123L);
        walletService.createWallet(wallet);
        Wallet retrievedWallet = walletService.getWalletById(4L);
        assertThat(retrievedWallet).isNotNull();
        assertThat(retrievedWallet.getId()).isEqualTo(4L);
    }

//    @Test
//    public void testGetWalletsByUserId() {
//        Wallet wallet1 = new Wallet("citi4", 5L, "INR", 123123L);
//        Wallet wallet2 = new Wallet("citi5", 6L, "INR", 123123L);
//        walletService.createWallet(wallet1);
//        walletService.createWallet(wallet2);
//        List<Wallet> wallets = walletService.getWalletsByUserId(123L);
//        assertThat(wallets).hasSize(2);
//    }

    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet("citi6", 7L, "INR", 123L);
        walletService.createWallet(wallet);
        Transaction transaction = walletService.deposit(7L, 100);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transaction.getAmount()).isEqualTo(100);
        assertThat(walletService.getBalance(7L)).isEqualTo(100);
    }

    @Test
    public void testWithdraw() {
        Wallet wallet = new Wallet("Dhruv", 8L, "INR", 123L);
        wallet.setBalance(200);
        walletService.createWallet(wallet);
        Transaction transaction = walletService.withdraw(8L, 100);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getType()).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(transaction.getAmount()).isEqualTo(100);
        assertThat(walletService.getBalance(8L)).isEqualTo(100);
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        Wallet wallet = new Wallet("citi7", 9L, "INR", 123L);
        wallet.setBalance(50);
        walletService.createWallet(wallet);
        assertThrows(IllegalArgumentException.class, () -> walletService.withdraw(9L, 100));
    }
}
