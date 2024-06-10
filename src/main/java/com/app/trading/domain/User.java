package com.app.trading.domain;

import java.math.BigDecimal;
import java.util.*;

public class User {
    private final UUID id;
    private final String name;
    private final String email;
    private final String address;
    private final Map<Currency, BigDecimal> balances;
    private List<Transaction> transactionHistory;
    private TradingSystem tradingSystem;

    public User(UUID id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.balances = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public User(String name, String email, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.address = address;
        this.balances = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public void setTradingSystem(TradingSystem tradingSystem) {
        this.tradingSystem = tradingSystem;
    }

    public void setBalances(){
        this.transactionHistory.forEach(transaction -> {
            if (TransactionType.BUY.equals(transaction.getTransactionType())){
                addBalance(transaction.getCurrency(), transaction.getValue(), transaction.getPrice());
                subBalance(Currency.USD, new BigDecimal(1), transaction.getValue().multiply(transaction.getPrice()));
            }
            else if (TransactionType.SELL.equals(transaction.getTransactionType())){
                subBalance(transaction.getCurrency(), transaction.getValue(), transaction.getPrice());
                addBalance(Currency.USD, new BigDecimal(1), transaction.getValue().multiply(transaction.getPrice()));
            }
            else if(TransactionType.REWARD.equals(transaction.getTransactionType())){
                addBalance(Currency.USD, new BigDecimal(1), transaction.getValue().multiply(transaction.getPrice()));
            }
        });
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
        setBalances();
    }

    public UUID getId() {
        return id;
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
