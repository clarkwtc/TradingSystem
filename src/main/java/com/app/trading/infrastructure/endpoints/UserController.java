package com.app.trading.infrastructure.endpoints;

import com.app.trading.application.CreateUserUseCase;
import com.app.trading.application.TransactionHistoryUseCase;
import com.app.trading.application.UserTransactionUseCase;
import com.app.trading.application.parameters.CreateUserUseCaseParameter;
import com.app.trading.application.parameters.TransactionHistoryParameter;
import com.app.trading.application.parameters.UserTransactionParameter;
import com.app.trading.domain.events.CreateUserEvent;;
import com.app.trading.domain.events.TransactionHistoryEvent;
import com.app.trading.infrastructure.dto.CreateUserDTO;
import com.app.trading.infrastructure.dto.TransactionHistoryDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    CreateUserUseCase createUserUseCase;
    @Autowired
    UserTransactionUseCase userTransactionUseCase;
    @Autowired
    TransactionHistoryUseCase transactionHistoryUseCase;

    @Getter
    @Setter
    public static class CreateRequest {
        private String name;
        private String email;
        private String address;
    }

    @PostMapping
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody CreateRequest request){
        CreateUserUseCaseParameter createUserUseCaseParameter = new CreateUserUseCaseParameter(request.name, request.email, request.address);
        CreateUserEvent event = createUserUseCase.execute(createUserUseCaseParameter);
        return new ResponseEntity<>(CreateUserDTO.toDTO(event), HttpStatus.CREATED);
    }

    @Getter
    @Setter
    public static class TransactionRequest {
        private Double amount;
        private String action;
    }

    @PostMapping("/{id}/transaction")
    public ResponseEntity<Object> transaction(@PathVariable String id, @RequestBody TransactionRequest transactionRequest){
        UserTransactionParameter userTransactionParameter = new UserTransactionParameter(UUID.fromString(id), BigDecimal.valueOf(transactionRequest.getAmount()), transactionRequest.getAction());
        userTransactionUseCase.execute(userTransactionParameter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/transactionHistory")
    public ResponseEntity<TransactionHistoryDTO> transactionHistory(@PathVariable String id){
        TransactionHistoryParameter transactionHistoryParameter = new TransactionHistoryParameter(UUID.fromString(id));
        TransactionHistoryEvent event = transactionHistoryUseCase.execute(transactionHistoryParameter);
        return new ResponseEntity<>(TransactionHistoryDTO.toDTO(event), HttpStatus.OK);
    }
}
