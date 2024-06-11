package com.app.trading.application;

import com.app.trading.application.parameters.CreateUserUseCaseParameter;
import com.app.trading.domain.IUseCase;
import com.app.trading.domain.TradingSystem;
import com.app.trading.domain.User;
import com.app.trading.domain.events.CreateUserEvent;
import com.app.trading.infrastructure.StartupTimeBean;
import com.app.trading.infrastructure.TransactionRepository;
import com.app.trading.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase implements IUseCase<CreateUserUseCaseParameter, CreateUserEvent> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private StartupTimeBean startupTimeBean;

    @Override
    public CreateUserEvent execute(CreateUserUseCaseParameter parameter) {
        Optional<User> userData = userRepository.findByAddress(parameter.address());
        User user = userData.orElseGet(() -> saveUser(parameter));
        return new CreateUserEvent(user);
    }

    private User saveUser(CreateUserUseCaseParameter parameter){
        TradingSystem tradingSystem = new TradingSystem(startupTimeBean.getStartupTime());
        tradingSystem.addNewUser(parameter.name(), parameter.email(), parameter.address());
        User user = tradingSystem.getUsers().get(0);
        User result = userRepository.save(user);
        transactionRepository.save(user.getId(), user.getTransactionHistory().get(0));
        return result;
    }
}
