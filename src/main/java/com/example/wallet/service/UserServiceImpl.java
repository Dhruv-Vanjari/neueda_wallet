package com.example.wallet.service;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final List<User> usersList = new ArrayList<>();

    @Override
    public Boolean createUser(User user) {
        usersList.add(user);
        return true;
    }

    @Override
    public User getUserById(Long userId) throws NoSuchElementException {
        return usersList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
    }

    @Override
    public User getUserByName(String name) {
        // Implementation goes here
        return usersList.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + name));
    }

    @Override
    public void deleteUser(Long userId) throws NoSuchElementException {
        User user = getUserById(userId);
        usersList.remove(user);
    }


    @Override
    public User addWalletToUser(Long userId, Wallet wallet) throws NoSuchElementException {
        User user = getUserById(userId);
        user.addWallet(wallet);
        return user;
    }

    @Override
    public void removeWalletFromUser(Long userId, Long walletId) throws NoSuchElementException {
        User user = getUserById(userId);
        Wallet wallet = user.getWallets().stream()
                .filter(w -> w.getId().equals(walletId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Wallet not found with id " + walletId));
        user.removeWallet(wallet);
    }
}


//package com.example.wallet.service;
//
//import com.example.wallet.model.User;
//import com.example.wallet.model.Wallet;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Service
//public class UserServiceImpl implements UserServiceInterface {
//
//    List<User> usersList = new ArrayList<>();
//
//    @Override
//    public Boolean createUser(User user) {
//        usersList.add(user);
//        return true;
//    }
//
//    @Override
//    public User getUserById(Long userId) throws NoSuchElementException{
//        return usersList.stream()
//                .filter(user -> user.getId().equals(userId))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
//    }
//
//    @Override
//    public User getUserByName(String name) {
//        return null;
//    }
//
//
//
//    @Override
//    public User addWalletToUser(Long userId, Wallet wallet) {
//        return null;
//    }
//
//    @Override
//    public void removeWalletFromUser(Long userId, Long walletId) {
//
//    }
//
//    public void deleteUser(Long id) {
//    }
//}
