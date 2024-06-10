package com.app.trading.infrastructure;

import com.app.trading.domain.events.CreateUserEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateUserDTO {
    public String id;

    public CreateUserDTO(String id) {
        this.id = id;
    }

    public static CreateUserDTO toDTO(CreateUserEvent event){
        return new CreateUserDTO(event.user().getId().toString());
    }
}
