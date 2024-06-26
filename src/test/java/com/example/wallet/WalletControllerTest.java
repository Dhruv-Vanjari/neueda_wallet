package com.example.wallet;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.Wallet;
import com.example.wallet.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @DirtiesContext
    @Test
    public void testCreateWallet() {
        Wallet wallet = new Wallet("icici1", 156L, "INR", 1231234L);
        ResponseEntity<Boolean> response = restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);

        // Debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        // Assertions
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(true);
    }

    @DirtiesContext
    @Test
    public void testDeleteWallet() {
        Wallet wallet = new Wallet("citi2", 2L, "INR", 123123L);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        restTemplate.delete("/api/wallets/2");
        ResponseEntity<Wallet> response = restTemplate.getForEntity("/api/wallets/2", Wallet.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @DirtiesContext
    @Test
    public void testGetBalance() {
        Wallet wallet = new Wallet("citi3", 3L, "INR", 123123L);
        wallet.setBalance(500);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        ResponseEntity<Integer> response = restTemplate.getForEntity("/api/wallets/3/balance", Integer.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(500);
    }

    @DirtiesContext
    @Test
    public void testGetWalletById() {
        Wallet wallet = new Wallet("Tanaya", 4L, "INR", 123123L);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        ResponseEntity<Wallet> response = restTemplate.getForEntity("/api/wallets/4", Wallet.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(4L);
    }

    @DirtiesContext
    @Test
    public void testGetWalletsByUserId() {
        Wallet wallet1 = new Wallet("citi4", 5L, "INR", 123123L);
        Wallet wallet2 = new Wallet("citi5", 6L, "INR", 123123L);
        restTemplate.postForEntity("/api/wallets", wallet1, Boolean.class);
        restTemplate.postForEntity("/api/wallets", wallet2, Boolean.class);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/wallets/user/123123", List.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
    }

    @DirtiesContext
    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet("citi6", 7L, "INR", 12312L);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        ResponseEntity<Transaction> response = restTemplate.postForEntity("/api/wallets/7/deposit", 100, Transaction.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(response.getBody().getAmount()).isEqualTo(100);
        ResponseEntity<Integer> balanceResponse = restTemplate.getForEntity("/api/wallets/7/balance", Integer.class);
        assertThat(balanceResponse.getBody()).isEqualTo(100);
    }

    @DirtiesContext
    @Test
    public void testWithdraw() {
        Wallet wallet = new Wallet("Dhruv", 8L, "INR", 123L);
        wallet.setBalance(200);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        ResponseEntity<Transaction> response = restTemplate.postForEntity("/api/wallets/8/withdraw", 100, Transaction.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(response.getBody().getAmount()).isEqualTo(100);
        ResponseEntity<Integer> balanceResponse = restTemplate.getForEntity("/api/wallets/8/balance", Integer.class);
        assertThat(balanceResponse.getBody()).isEqualTo(100);
    }

    @DirtiesContext
    @Test
    public void testWithdrawInsufficientBalance() {
        Wallet wallet = new Wallet("citi7", 9L, "INR", 123L);
        wallet.setBalance(50);
        restTemplate.postForEntity("/api/wallets", wallet, Boolean.class);
        HttpEntity<Integer> request = new HttpEntity<>(100);
        ResponseEntity<Transaction> response = restTemplate.exchange("/api/wallets/9/withdraw", HttpMethod.POST, request, Transaction.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}