package com.app.trading.infrastructure.endpoints;

import com.app.trading.application.CreateUseCase;
import com.app.trading.application.parameters.CreateUseCaseParameter;
import com.app.trading.domain.events.CreateUserEvent;
import com.app.trading.infrastructure.CreateUserDTO;
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
    CreateUseCase createUseCase;

    @Getter
    @Setter
    public static class CreateRequest {
        private String name;
        private String email;
        private String address;
    }

    @PostMapping
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody CreateRequest request){
        CreateUseCaseParameter createUseCaseParameter = new CreateUseCaseParameter(request.name, request.email, request.address);
        CreateUserEvent event = createUseCase.execute(createUseCaseParameter);
        return new ResponseEntity<>(CreateUserDTO.toDTO(event), HttpStatus.CREATED);
    }
}
