package com.example.wallet;

import com.example.wallet.controller.TransactionController;
import com.example.wallet.model.Transaction;
import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.TransactionServiceImpl;
import com.example.wallet.service.UserServiceImpl;
import com.example.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;  // Updated import

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WalletServiceImpl walletService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        User dhruvi = new User(1231235L, "vanjaridhruvi", "vanjaridhruvi@gmail.com", "qwertyui");
        User tanay = new User(4564567L, "shelketanay", "shelketanay@gmail.com", "asdfghjk");

        userService.createUser(dhruvi);
        userService.createUser(tanay);

        Wallet wallet_dhruvi = new Wallet("main_d", 45456L, "USD", 1231235L);
        Wallet wallet_tanay = new Wallet("main_t", 56567L, "USD", 4564567L);

        walletService.createWallet(wallet_dhruvi);
        walletService.createWallet(wallet_tanay);

        transactionService.deposit(45456L, 100);
        transactionService.transfer(45456L, 56567L, 50);
    }

    @Test
    public void testTransfer() {
        String url = "http://localhost:" + port + "/transactions/transfer?fromWalletId=45456&toWalletId=56567&amount=20";
        ResponseEntity<Transaction> response = restTemplate.postForEntity(url, null, Transaction.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        assertThat(walletService.getBalance(45456L)).isEqualTo(30);
        assertThat(walletService.getBalance(56567L)).isEqualTo(70);

        assertThat(transactionService.getTransactionsByWalletId(45456L)).hasSize(3);
        assertThat(transactionService.getTransactionsByWalletId(56567L)).hasSize(2);
    }

    @Test
    public void testDeposit() {
        String url = "http://localhost:" + port + "/transactions/deposit?walletId=4545&amount=50";
        ResponseEntity<Transaction> response = restTemplate.postForEntity(url, null, Transaction.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        assertThat(walletService.getBalance(4545L)).isEqualTo(80);

        assertThat(transactionService.getTransactionsByWalletId(4545L)).hasSize(4);
    }

    @Test
    public void testGetTransactionById() {
        String url = "http://localhost:" + port + "/transactions/1";
        ResponseEntity<Transaction> response = restTemplate.getForEntity(url, Transaction.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetTransactionsByWalletId() {
        String url = "http://localhost:" + port + "/transactions/wallet/4545";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    public void testGetTransactionsByUserId() {
        String url = "http://localhost:" + port + "/transactions/user/123123";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }
}
