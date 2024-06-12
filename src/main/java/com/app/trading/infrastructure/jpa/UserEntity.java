package com.app.trading.infrastructure.jpa;

import com.app.trading.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private long createdAt;

    public static User toDomain(UserEntity entity){
        return new User(UUID.fromString(entity.getId()), entity.getName(), entity.getEmail(), entity.getAddress());
    }

    public static UserEntity toEntity(User user){
        return UserEntity.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .createdAt(new Date().getTime())
                .build();
    }
}
