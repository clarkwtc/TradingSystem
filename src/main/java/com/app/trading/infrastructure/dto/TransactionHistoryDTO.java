package com.app.trading.infrastructure.dto;

import com.app.trading.domain.Transaction;
import com.app.trading.domain.events.TransactionHistoryEvent;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TransactionHistoryDTO {
    public List<TransactionDTO> transactions;

    public TransactionHistoryDTO(List<Transaction> transactions) {
        this.transactions = transactions.stream().map(TransactionDTO::toDTO).collect(Collectors.toList());
    }

    public static TransactionHistoryDTO toDTO(TransactionHistoryEvent event){
        return new TransactionHistoryDTO(event.transactions());
    }
}
