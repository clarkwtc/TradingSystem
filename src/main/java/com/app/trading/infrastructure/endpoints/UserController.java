package com.app.trading.infrastructure.endpoints;

import com.app.trading.application.CreateUserUseCase;
import com.app.trading.application.parameters.CreateUserUseCaseParameter;
import com.app.trading.domain.events.CreateUserEvent;
import com.app.trading.infrastructure.dto.CreateUserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    CreateUserUseCase createUserUseCase;

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
}
