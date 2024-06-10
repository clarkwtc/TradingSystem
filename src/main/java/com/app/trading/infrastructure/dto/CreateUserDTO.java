package com.app.trading.infrastructure.dto;

import com.app.trading.domain.events.CreateUserEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    public String id;

    public static CreateUserDTO toDTO(CreateUserEvent event){
        return new CreateUserDTO(event.user().getId().toString());
    }
}
