package com.app.trading.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TradingSystem {
    private final List<User> users;
    private final PricingSystem pricingSystem;

    public TradingSystem() {
        this.users = new ArrayList<>();
        this.pricingSystem = new PricingSystem(new BigDecimal(100), Currency.BTC);
        pricingSystem.start();
    }

    public PricingSystem getPricingSystem() {
        return pricingSystem;
    }

    public void addUser(String name, String email, String address){
        User user = new User(name, email, address);
        giveReward(user);
        user.setTradingSystem(this);
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    private void giveReward(User user){
        user.deposit(new BigDecimal(1), new BigDecimal(1000), Currency.USD, TransactionType.REWARD);
    }

    public void deleteUser(String address){
        users.stream().filter(user -> user.getAddress().equals(address)).findFirst().ifPresent(users::remove);
    }
}
