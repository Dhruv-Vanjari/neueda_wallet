package com.example.wallet;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.UserServiceImpl;
import com.example.wallet.service.WalletServiceImpl;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest
{

    @Autowired
    UserServiceImpl userService;

    @DirtiesContext
    @BeforeEach
    public void setup() {
        User user1 = new User(1231234L, "vanjaridhruv", "vanjaridhruv@gmail.com", "qwertyui");
        User user2 = new User(456789L, "shelketanaya", "anjaridhruv@gmail.com", "asdfghj");
        User user3 =new User(789789L, "prerak", "prerak@gmail.com", "123456789");

        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        Wallet wallet = new Wallet("Travel", 4545L, "USD", 123123L);
        userService.addWalletToUser(123123L, wallet);
    }



    @DirtiesContext
    @Test
    public void testCreateUser() {
        User user = new User(1231234L, "vanjaridhruv4", "vanjaridhruv4@gmail.com", "qwertyui");
        User createdUser = userService.createUser(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(user.getId());
        assertThat(createdUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(createdUser.getPassword()).isEqualTo(user.getPassword());
    }

    @DirtiesContext
    @Test
    public void testGetUserById() {
        User user = userService.getUserById(123123L);
        assertThat(user).isNotNull();

        assertThat(user.getId()).isEqualTo(123123L);
    }

    @Test
    public void testGetUserByName() {
        User user = userService.getUserByName("prerak");

        assertThat(user.getId()).isEqualTo(789789L);
    }

    @Test
    @DirtiesContext
    public void testDeleteUser() {
        userService.deleteUser(789789L);
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(789789L));
    }

    @DirtiesContext
    @Test
    public void testAddWalletToUser() {
        User user = userService.getUserById(123123L);
        List<Wallet> wallets = user.getWallets();
        Assertions.assertThat(wallets).hasSize(1);
    }

    @DirtiesContext
    @Test
    public void testDeleteWalletFromUser() {
        userService.removeWalletFromUser(123123L, 4545L);

        Assertions.assertThat(userService.getUserById(123123L).getWallets()).hasSize(0);
    }


}
