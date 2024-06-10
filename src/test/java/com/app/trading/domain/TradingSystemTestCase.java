package com.app.trading.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class TradingSystemTestCase {
    private TradingSystem tradingSystem;
    @BeforeEach
    void setUp() {
        this.tradingSystem = new TradingSystem();
    }

    @Test
    public void addNewUser(){
        // Given
        String name = "clark";
        String email = "cc@gmail.com";
        String address = UUID.randomUUID().toString();

        // When
        tradingSystem.addNewUser(name, email, address);

        // Then
        Assertions.assertEquals(1, tradingSystem.getUsers().size());
        User user = tradingSystem.getUsers().get(0);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(address, user.getAddress());
        Assertions.assertEquals(1, user.getTransactionHistory().size());
        Assertions.assertEquals(new BigDecimal(1000), user.getBalances(Currency.USD));
    }

    @Test
    public void deleteUser(){
        // Given
        String name = "clark";
        String email = "cc@gmail.com";
        String address = "TW";
        tradingSystem.addNewUser(name, email, address);
        User user = tradingSystem.getUsers().get(0);

        // When
        tradingSystem.deleteUser(user.getAddress());

        // Then
        Assertions.assertEquals(0, tradingSystem.getUsers().size());
    }
}
