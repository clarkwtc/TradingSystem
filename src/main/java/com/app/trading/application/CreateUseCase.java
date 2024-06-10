package com.app.trading.application;

import com.app.trading.application.parameters.CreateUseCaseParameter;
import com.app.trading.domain.IUseCase;
import com.app.trading.domain.TradingSystem;
import com.app.trading.domain.User;
import com.app.trading.domain.events.CreateUserEvent;
import com.app.trading.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUseCase implements IUseCase<CreateUserEvent, CreateUseCaseParameter> {
    @Autowired
    UserRepository userRepository;

    @Override
    public CreateUserEvent execute(CreateUseCaseParameter parameter) {
        Optional<User> userData = userRepository.findByAddress(parameter.address());
        User user = userData.orElseGet(() -> saveUser(parameter));
        return new CreateUserEvent(user);
    }

    private User saveUser(CreateUseCaseParameter parameter){
        TradingSystem tradingSystem = new TradingSystem();
        tradingSystem.addUser(parameter.name(), parameter.email(), parameter.address());
        return userRepository.save(tradingSystem.getUsers().get(0));
    }
}
