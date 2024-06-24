package com.example.wallet;

import com.example.wallet.model.User;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.TransactionServiceImpl;
import com.example.wallet.service.UserServiceImpl;
import com.example.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionServiceTest {

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    WalletServiceImpl walletService;



    @DirtiesContext
    @Test
    public void testTransfer() {
        User dhruv = new User(123123L, "vanjaridhruv", "vanjaridhruv@gmail.com", "qwertyui");
        User tanaya = new User(456456L, "shelketanaya", "shelketanaya@gmail.com", "asdfghjk");

        userService.createUser(dhruv);
        userService.createUser(tanaya);

        Wallet wallet_dhruv = new Wallet("main", 4545L, "USD", 123123L);
        Wallet wallet_tanaya = new Wallet("main", 5656L, "USD", 456456L);

        walletService.createWallet(wallet_dhruv);
        walletService.createWallet(wallet_tanaya);

        transactionService.deposit(4545L, 100);
        transactionService.transfer(4545L, 5656L, 50);

        assertThat(walletService.getBalance(4545L)).isEqualTo(50);
        assertThat(walletService.getBalance(5656L)).isEqualTo(50);

        org.assertj.core.api.Assertions.assertThat(transactionService.getTransactionsByWalletId(4545L)).hasSize(2);
        org.assertj.core.api.Assertions.assertThat(transactionService.getTransactionsByWalletId(5656L)).hasSize(1);

    }

}
