package com.app.trading.domain;

import java.math.BigDecimal;

public class Transaction {
    private final BigDecimal price;
    private final BigDecimal value;
    private final Currency currency;
    private final TransactionType transactionType;

    public Transaction(BigDecimal price, BigDecimal value, Currency currency, TransactionType transactionType) {
        this.price = price;
        this.value = value;
        this.currency = currency;
        this.transactionType = transactionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
