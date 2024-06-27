package com.example.wallet;

import com.example.wallet.model.Transaction;
import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.model.TransactionType;
import com.example.wallet.service.UserServiceImpl;
import com.example.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WalletServiceImpl walletService;

    @Autowired
    private UserServiceImpl userService;


    @DirtiesContext
    @Test
    public void testCreateWallet() {
        // Step 1: Create User first
        User user = new User(1231234L, "John Doe", "john.doe@example.com", "password");
        ResponseEntity<User> userResponse = restTemplate.postForEntity("/api/users", user, User.class);
        assertThat(userResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userResponse.getBody()).isNotNull();
        assertThat(userResponse.getBody().getId()).isEqualTo(1231234L);

        // Step 2: Create Wallet associated with the user
        Wallet wallet = new Wallet("icici1", 156L, "INR", 1231234L);
        ResponseEntity<Wallet> walletResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);

        // Assertions for wallet creation
        assertThat(walletResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletResponse.getBody()).isNotNull();
        assertThat(walletResponse.getBody().getId()).isEqualTo(156L);
        assertThat(walletResponse.getBody().getName()).isEqualTo("icici1");
        assertThat(walletResponse.getBody().getCurrency()).isEqualTo("INR");
    }

    @DirtiesContext
    @Test
    public void testDeleteWallet() {
        // Step 1: Create the Wallet
        Wallet wallet = new Wallet("citi2", 2L, "INR", 123123L);

        // Step 2: Delete the Wallet
        restTemplate.delete("/api/wallets/2");


        // Step 3: Verify the Wallet is Deleted
        ResponseEntity<Wallet> response = restTemplate.getForEntity("/api/wallets/2", Wallet.class);
        assertThrows(NoSuchElementException.class, () -> walletService.getWalletById(3L));
    }




    @DirtiesContext
    @Test
    public void testGetBalance() {
        User user = new User(123123L, "John Doe", "john.doe@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Step 1: Create the Wallet
        Wallet wallet = new Wallet("citi3", 3L, "INR", 123123L);
        wallet.setBalance(500);
        ResponseEntity<Wallet> walletCreateResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);



        // Step 2: Verify the Balance Retrieval
        ResponseEntity<Integer> response = restTemplate.getForEntity("/api/wallets/3/balance", Integer.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(500);
    }

    @DirtiesContext
    @Test
    public void testGetWalletById() {
        // Step 1: Create the User
        User user = new User(123123L, "John Doe", "john.doe@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Step 1: Create the Wallet
        Wallet wallet = new Wallet("Tanayaa", 44L, "INR", 123123L);
        ResponseEntity<Wallet> walletCreateResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);

        // Step 2: Retrieve the Wallet by ID
        ResponseEntity<Wallet> response = restTemplate.getForEntity("/api/wallets/44", Wallet.class);

        // Step 3: Assert the retrieval was successful
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(44L);
        assertThat(response.getBody().getName()).isEqualTo("Tanayaa");
        assertThat(response.getBody().getCurrency()).isEqualTo("INR");
        assertThat(response.getBody().getUserId()).isEqualTo(123123L);
    }

    @DirtiesContext
    @Test
    public void testGetWalletsByUserId() {
        // Step 1: Create the User
        User user = new User(123123L, "John Doe", "john.doe@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Assert user creation was successful
        assertThat(userCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userCreateResponse.getBody()).isNotNull();
        assertThat(userCreateResponse.getBody().getId()).isEqualTo(123123L);

        // Step 2: Create Wallets associated with the user
        Wallet wallet1 = new Wallet("citi4", 5L, "INR", 123123L);
        Wallet wallet2 = new Wallet("citi5", 6L, "INR", 123123L);
        ResponseEntity<Wallet> walletCreateResponse1 = restTemplate.postForEntity("/api/wallets", wallet1, Wallet.class);
        ResponseEntity<Wallet> walletCreateResponse2 = restTemplate.postForEntity("/api/wallets", wallet2, Wallet.class);

        // Assert wallet creation was successful
        assertThat(walletCreateResponse1.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletCreateResponse1.getBody()).isNotNull();
        assertThat(walletCreateResponse1.getBody().getId()).isEqualTo(5L);

        assertThat(walletCreateResponse2.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletCreateResponse2.getBody()).isNotNull();
        assertThat(walletCreateResponse2.getBody().getId()).isEqualTo(6L);

        // Step 3: Verify the wallets are correctly retrieved by user ID
        ResponseEntity<List> response = restTemplate.getForEntity("/api/wallets/user/123123", List.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
    }

    @DirtiesContext
    @Test
    public void testDeposit() {
        // Step 1: Create the User
        User user = new User(123123L, "John Doe", "john.doe@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Assert user creation was successful
        assertThat(userCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userCreateResponse.getBody()).isNotNull();
        assertThat(userCreateResponse.getBody().getId()).isEqualTo(123123L);

        // Step 2: Create the Wallet
        Wallet wallet = new Wallet("citi6", 7L, "INR", 123123L); // Ensure userId is correctly set to 123123L
        ResponseEntity<Wallet> walletCreateResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);

        // Assert wallet creation was successful
        assertThat(walletCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletCreateResponse.getBody()).isNotNull();
        assertThat(walletCreateResponse.getBody().getId()).isEqualTo(7L);

        // Step 3: Make the Deposit
        Integer depositAmount = 100;
        ResponseEntity<Transaction> response = restTemplate.postForEntity("/api/wallets/7/deposit", depositAmount, Transaction.class);

        // Log the deposit response
        System.out.println("Deposit Response: " + response);

        // Assert the deposit was successful
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(response.getBody().getAmount()).isEqualTo(100);

        // Step 4: Verify the Wallet Balance
        ResponseEntity<Integer> balanceResponse = restTemplate.getForEntity("/api/wallets/7/balance", Integer.class);

        // Log the balance response
        System.out.println("Balance Response: " + balanceResponse);

        // Assert the balance is correct
        assertThat(balanceResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(balanceResponse.getBody()).isEqualTo(100);
    }


    @DirtiesContext
    @Test
    public void testWithdraw() {
        // Step 1: Create the User
        User user = new User(123L, "Dhruv Sharma", "dhruv.sharma@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Assert user creation was successful
        assertThat(userCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userCreateResponse.getBody()).isNotNull();
        assertThat(userCreateResponse.getBody().getId()).isEqualTo(123L);

        // Step 2: Create the Wallet with an initial balance
        Wallet wallet = new Wallet("Dhruv", 8L, "INR", 123L);
        wallet.setBalance(200);
        ResponseEntity<Wallet> walletCreateResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);

        // Assert wallet creation was successful
        assertThat(walletCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletCreateResponse.getBody()).isNotNull();
        assertThat(walletCreateResponse.getBody().getId()).isEqualTo(8L);

        // Step 3: Make the Withdraw
        Integer withdrawAmount = 100;
        ResponseEntity<Transaction> response = restTemplate.postForEntity("/api/wallets/8/withdraw", withdrawAmount, Transaction.class);

        // Log the withdraw response
        System.out.println("Withdraw Response: " + response);

        // Assert the withdraw was successful
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(response.getBody().getAmount()).isEqualTo(100);

        // Step 4: Verify the Wallet Balance
        ResponseEntity<Integer> balanceResponse = restTemplate.getForEntity("/api/wallets/8/balance", Integer.class);

        // Log the balance response
        System.out.println("Balance Response: " + balanceResponse);

        // Assert the balance is correct
        assertThat(balanceResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(balanceResponse.getBody()).isEqualTo(100);
    }


    @DirtiesContext
    @Test

    public void testWithdrawInsufficientBalance() {
        // Step 1: Create the User
        User user = new User(123L, "Jane Doe", "jane.doe@example.com", "password");
        ResponseEntity<User> userCreateResponse = restTemplate.postForEntity("/api/users", user, User.class);

        // Assert user creation was successful
        assertThat(userCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(userCreateResponse.getBody()).isNotNull();
        assertThat(userCreateResponse.getBody().getId()).isEqualTo(123L);

        // Step 2: Create the Wallet with insufficient balance
        Wallet wallet = new Wallet("citi7", 9L, "INR", 123L);
        wallet.setBalance(50);
        ResponseEntity<Wallet> walletCreateResponse = restTemplate.postForEntity("/api/wallets", wallet, Wallet.class);

        // Assert wallet creation was successful
        assertThat(walletCreateResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(walletCreateResponse.getBody()).isNotNull();
        assertThat(walletCreateResponse.getBody().getId()).isEqualTo(9L);

        // Step 3: Attempt to Withdraw more than the balance
        Integer withdrawAmount = 100;
        HttpEntity<Integer> request = new HttpEntity<>(withdrawAmount);
        ResponseEntity<Transaction> response = restTemplate.exchange("/api/wallets/9/withdraw", HttpMethod.POST, request, Transaction.class);

        // Log the withdraw response
        System.out.println("Withdraw Insufficient Balance Response: " + response);

        // Assert the withdraw failed due to insufficient balance
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

}