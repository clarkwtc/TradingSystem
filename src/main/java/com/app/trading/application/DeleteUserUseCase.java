package com.app.trading.application;

import com.app.trading.application.exceptions.NotExistUserException;
import com.app.trading.application.parameters.DeleteUserParameter;
import com.app.trading.domain.IUseCase;
import com.app.trading.domain.User;
import com.app.trading.domain.events.DeleteUserEvent;
import com.app.trading.infrastructure.TransactionRepository;
import com.app.trading.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteUserUseCase implements IUseCase<DeleteUserParameter, DeleteUserEvent> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public DeleteUserEvent execute(DeleteUserParameter parameter) {
        UUID userId = parameter.userId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new NotExistUserException();
        }

        userRepository.deleteById(userId);
        transactionRepository.deleteByUserId(userId);
        return new DeleteUserEvent(optionalUser.get());
    }
}
