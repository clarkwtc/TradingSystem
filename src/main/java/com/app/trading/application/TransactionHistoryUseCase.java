package com.app.trading.application;

import com.app.trading.application.parameters.TransactionHistoryParameter;
import com.app.trading.domain.IUseCase;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.events.TransactionHistoryEvent;
import com.app.trading.infrastructure.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryUseCase implements IUseCase<TransactionHistoryParameter, TransactionHistoryEvent> {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionHistoryEvent execute(TransactionHistoryParameter parameter) {
        List<Transaction> transactions = transactionRepository.findByUserId(parameter.userId());
        return new TransactionHistoryEvent(transactions);
    }
}
