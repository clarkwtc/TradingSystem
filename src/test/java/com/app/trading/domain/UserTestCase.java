package com.app.trading.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;


public class UserTestCase {
    TradingSystem tradingSystem;
    @BeforeEach
    void setUp(){
        this.tradingSystem = new TradingSystem();
        tradingSystem.addUser("clark", "cc@email.com", UUID.randomUUID().toString());
    }

    @Test
    void deposit(){
        // Given
        BigDecimal money = new BigDecimal(100);
        User user = tradingSystem.getUsers().get(0);

        // When
        user.deposit(new BigDecimal(1), money, Currency.USD, TransactionType.DEPOSIT);

        // Then
        Assertions.assertEquals(new BigDecimal(1100), user.getBalances(Currency.USD));
    }

    @Test
    void buy(){
        // Given
        User user = tradingSystem.getUsers().get(0);
        BigDecimal amount = new BigDecimal(2);

        // When
        user.buy(amount);

        // Then
        Assertions.assertEquals(new BigDecimal(800), user.getBalances(Currency.USD));
        Assertions.assertEquals(new BigDecimal(200), user.getBalances(Currency.BTC));
    }

    @Test
    void buyFail(){
        // Given
        User user = tradingSystem.getUsers().get(0);
        BigDecimal amount = new BigDecimal(11);

        // When
        user.buy(amount);

        // Then
        Assertions.assertEquals(new BigDecimal(1000), user.getBalances(Currency.USD));
        Assertions.assertNull(user.getBalances(Currency.BTC));
    }

    @Test
    void sell(){
        // Given
        User user = tradingSystem.getUsers().get(0);
        BigDecimal buyAmount = new BigDecimal(3);
        user.buy(buyAmount);
        BigDecimal sellAmount = new BigDecimal(2);

        // When
        user.sell(sellAmount);

        // Then
        Assertions.assertEquals(new BigDecimal(900), user.getBalances(Currency.USD));
        Assertions.assertEquals(new BigDecimal(100), user.getBalances(Currency.BTC));
    }

    @Test
    void sellFail(){
        // Given
        User user = tradingSystem.getUsers().get(0);
        BigDecimal amount = new BigDecimal(2);

        // When
        user.sell(amount);

        // Then
        Assertions.assertEquals(new BigDecimal(1000), user.getBalances(Currency.USD));
        Assertions.assertNull(user.getBalances(Currency.BTC));
    }
}
