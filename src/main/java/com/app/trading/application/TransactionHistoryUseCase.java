package com.app.trading.application;

import com.app.trading.application.exceptions.NotExistUserException;
import com.app.trading.application.parameters.TransactionHistoryParameter;
import com.app.trading.domain.IUseCase;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.User;
import com.app.trading.domain.events.TransactionHistoryEvent;
import com.app.trading.infrastructure.TransactionRepository;
import com.app.trading.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionHistoryUseCase implements IUseCase<TransactionHistoryParameter, TransactionHistoryEvent> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionHistoryEvent execute(TransactionHistoryParameter parameter) {
        UUID userId = parameter.userId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new NotExistUserException();
        }

        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return new TransactionHistoryEvent(transactions);
    }
}
