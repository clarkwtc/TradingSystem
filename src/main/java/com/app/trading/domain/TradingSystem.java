package com.app.trading.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TradingSystem {
    private final List<User> users;
    private final PricingAlgorithm pricingAlgorithm;

    public TradingSystem(LocalDateTime startupTime) {
        this.users = new ArrayList<>();
        this.pricingAlgorithm = new PricingAlgorithm(new BigDecimal("100.0"), Currency.BTC, startupTime);
    }

    public PricingAlgorithm getPricingSystem() {
        return pricingAlgorithm;
    }

    public void addNewUser(String name, String email, String address){
        User user = new User(name, email, address);
        giveReward(user);
        addUser(user);
    }

    public void addUser(User user){
        user.setTradingSystem(this);
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    private void giveReward(User user){
        user.deposit(new BigDecimal("1.0"), new BigDecimal("1000.0"), Currency.USD, TransactionType.REWARD);
    }

    public void deleteUser(String address){
        users.stream().filter(user -> user.getAddress().equals(address)).findFirst().ifPresent(users::remove);
    }
}
