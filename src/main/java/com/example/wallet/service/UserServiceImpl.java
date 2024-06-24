package com.example.wallet.service;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserServiceInterface {

    List<User> usersList = new ArrayList<>();

    @Override
    public Boolean createUser(User user) {
        usersList.add(user);
        return true;
    }

    @Override
    public User getUserById(Long userId) throws NoSuchElementException{
        return usersList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public void DeleteUser(Long userId) {

    }

    @Override
    public User addWalletToUser(Long userId, Wallet wallet) {
        return null;
    }

    @Override
    public void removeWalletFromUser(Long userId, Long walletId) {

    }
}
