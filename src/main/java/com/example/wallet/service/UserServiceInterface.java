package com.example.wallet.service;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;

public interface UserServiceInterface {

    User createUser(User user);

    User getUserById(Long userId);
    User getUserByName(String name);

    void deleteUser(Long userId);

    User addWalletToUser(Long userId, Wallet wallet);
    void removeWalletFromUser(Long userId, Long walletId);


}

