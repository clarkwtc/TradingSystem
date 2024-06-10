package com.app.trading.application;

import com.app.trading.application.parameters.UserTransactionParameter;
import com.app.trading.domain.*;
import com.app.trading.domain.events.TransactionEvent;
import com.app.trading.infrastructure.TransactionRepository;
import com.app.trading.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserTransactionUseCase implements IUseCase<UserTransactionParameter, TransactionEvent> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionEvent execute(UserTransactionParameter parameter) {
        User user = findUser(parameter.userId());
        Transaction transaction = userTransaction(user, parameter.amount(), parameter.action());
        return new TransactionEvent(transaction);
    }

    private Transaction userTransaction(User user, BigDecimal amount, String actionString) {
        TransactionType action = TransactionType.valueOf(actionString.toUpperCase());
        if (TransactionType.BUY.equals(action)){
            user.buy(amount);
        }
        else if (TransactionType.SELL.equals(action)){
            user.sell(amount);
        }
        List<Transaction> transactionHistory = user.getTransactionHistory();
        return transactionRepository.save(user.getId(), transactionHistory.get(transactionHistory.size() - 1));
    }

    private User findUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if (optionalUser.isEmpty()){
            throw new RuntimeException();
        }

        TradingSystem tradingSystem = new TradingSystem();
        tradingSystem.addUser(optionalUser.get());
        User user = tradingSystem.getUsers().get(0);
        user.setTransactionHistory(transactions);
        return user;
    }
}
