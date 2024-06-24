package com.example.wallet;

import com.example.wallet.model.User;
import com.example.wallet.service.UserServiceImpl;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest
{

    @Autowired
    UserServiceImpl userService;



        @Test
        public void testCreateUser() {
            User user = new User(123123L, "vanjaridhruv", "vanjaridhruv@gmail.com", "qwertyui");
            assertThat(userService.createUser(user)).isEqualTo(true);
        }

        @Test
    public void testGetUserById() {
            User user = userService.getUserById(123123L);
            assertThat(user).isNotNull();

            assertThat(user.getId()).isEqualTo(123123L);
        }

}
