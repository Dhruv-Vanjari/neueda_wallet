package com.example.wallet.controller;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return null;}

    @PostMapping("/{userId}/wallets")
    public ResponseEntity<User> addWalletToUser(@PathVariable Long userId, @RequestBody Wallet wallet) {
        try {
            User user = userService.addWalletToUser(userId, wallet);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/wallets/{walletId}")
    public ResponseEntity<Void> removeWalletFromUser(@PathVariable Long userId, @PathVariable Long walletId) {
        try {
            userService.removeWalletFromUser(userId, walletId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


}