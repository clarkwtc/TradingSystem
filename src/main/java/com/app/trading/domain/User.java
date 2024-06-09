package com.app.trading.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private final String name;
    private final String email;
    private final String address;
    private final Map<Currency, BigDecimal> balances;
    private final List<Transaction> transactionHistory;
    private TradingSystem tradingSystem;

    public User(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.balances = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public void setTradingSystem(TradingSystem tradingSystem) {
        this.tradingSystem = tradingSystem;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getBalances(Currency currency) {
        return balances.get(currency);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    private void transaction(BigDecimal price, BigDecimal amount, Currency currency, TransactionType transactionType){
        Transaction transaction = new Transaction(price, amount, currency, transactionType);
        transactionHistory.add(transaction);

//        if (!balances.containsKey(currency)){
//            balances.put(currency, new BigDecimal(0));
//        }
//
//        if (TransactionType.BUY.equals(transactionType) || TransactionType.REWARD.equals(transactionType)){
//            balances.put(currency, balances.get(currency).add(transaction.getPrice().multiply(transaction.getValue())));
//        }
//        else if(TransactionType.SELL.equals(transactionType)){
//            balances.put(currency, balances.get(currency).subtract(transaction.getPrice().multiply(transaction.getValue())));
//        }
    }

    public void deposit(BigDecimal price, BigDecimal amount, Currency currency, TransactionType transactionType){
        transaction(price, amount, currency, transactionType);

        if (!balances.containsKey(currency)){
            balances.put(currency, new BigDecimal(0));
        }

        balances.put(currency, balances.get(currency).add(price.multiply(amount)));
    }

    private void addBalance(Currency currency, BigDecimal price, BigDecimal amount){
        if (!balances.containsKey(currency)){
            balances.put(currency, new BigDecimal(0));
        }

        balances.put(currency, balances.get(currency).add(price.multiply(amount)));
    }

    private void subBalance(Currency currency, BigDecimal price, BigDecimal amount){
        if (!balances.containsKey(currency)){
            balances.put(currency, new BigDecimal(0));
        }

        balances.put(currency, balances.get(currency).subtract(price.multiply(amount)));
    }

    public void buy(BigDecimal amount){
        BigDecimal marketPrice = tradingSystem.getPricingSystem().getPrice();
        BigDecimal totalValue = marketPrice.multiply(amount);
        if (balances.containsKey(Currency.USD) && balances.get(Currency.USD).compareTo(totalValue) != -1){
            transaction(marketPrice, amount, Currency.BTC, TransactionType.BUY);

            addBalance(Currency.BTC, marketPrice, amount);
            subBalance(Currency.USD, new BigDecimal(1), totalValue);
        }
    }

    public void sell(BigDecimal amount){
        BigDecimal marketPrice = tradingSystem.getPricingSystem().getPrice();
        BigDecimal totalValue = marketPrice.multiply(amount);
        if (balances.containsKey(Currency.BTC) && balances.get(Currency.BTC).compareTo(totalValue) != -1){
            transaction(marketPrice, amount, Currency.BTC, TransactionType.SELL);

            addBalance(Currency.USD, new BigDecimal(1), totalValue);
            subBalance(Currency.BTC, marketPrice, amount);
        }
    }
}
