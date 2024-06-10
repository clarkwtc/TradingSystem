package com.app.trading.infrastructure.dto;

import com.app.trading.domain.Currency;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    public BigDecimal price;
    public BigDecimal value;
    public Currency currency;
    public TransactionType transactionType;

    public static TransactionDTO toDTO(Transaction transaction){
        return new TransactionDTO(transaction.getPrice(), transaction.getValue(), transaction.getCurrency(), transaction.getTransactionType());
    }
}
