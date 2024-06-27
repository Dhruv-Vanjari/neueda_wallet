package com.example.wallet;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.UserServiceImpl;
import com.example.wallet.service.WalletServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WalletServiceImpl walletService;
    @BeforeEach
    public void setup() {
        // Perform any setup here if needed
    }

    @DirtiesContext
    @Test
    public void testCreateUser() {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password");
       // userService.createUser(user);

        ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getUsername()).isEqualTo("John Doe");
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@example.com");
    }

    @DirtiesContext
    @Test
    public void testGetUserById() {
        User user = new User(2L, "Jane Smith", "jane.smith@example.com", "password");
        //userService.createUser(user);
        ResponseEntity<User> response1 = restTemplate.postForEntity("/api/users", user, User.class);



        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/2", User.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getUsername()).isEqualTo("Jane Smith");
        assertThat(response.getBody().getEmail()).isEqualTo("jane.smith@example.com");
    }

    @DirtiesContext
    @Test
    public void testDeleteUser() {
        User user = new User(3L, "Mark Johnson", "mark.johnson@example.com", "password");
       // userService.createUser(user);

        restTemplate.delete("/api/users/3");

        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/3", User.class);

//        Assertions.assertThat(userService.getUserById(3L).getWallets()).hasSize(0);// Assuming 404 as user should not exist
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(3L));
    }

    @DirtiesContext
    @Test
    public void testAddWalletToUser() {
        User user = new User(145145L, "Emily Davis", "emily.davis@example.com", "password");
        //userService.createUser(user);
        ResponseEntity<User> response1 = restTemplate.postForEntity("/api/users", user, User.class);

        Wallet wallet = new Wallet("Main Wallet", 156589L, "USD", 145145L);
       // walletService.createWallet(wallet);
//        User user1 = userService.addWalletToUser(145145L, wallet);


        ResponseEntity<User> response = restTemplate.postForEntity("/api/users/145145/wallets", user, User.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getWallets()).hasSize(1);
    }

    @DirtiesContext
    @Test
    public void testRemoveWalletFromUser() {
        User user = new User(5L, "Michael Brown", "michael.brown@example.com", "password");
        Wallet wallet = new Wallet("Savings", 2L, "USD", 5L);

        user.addWallet(wallet);
        //userService.createUser(user);
        ResponseEntity<User> response1 = restTemplate.postForEntity("/api/users", user, User.class);


        restTemplate.delete("/api/users/5/wallets/2");
        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/5", User.class);

        assertAll(
                () -> assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(),
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getBody().getWallets()).hasSize(0)
        );
    }
}
