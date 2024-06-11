package com.app.trading.infrastructure;

import com.app.trading.domain.IUserRepository;
import com.app.trading.domain.User;
import com.app.trading.infrastructure.jpa.JpaUserRepository;
import com.app.trading.infrastructure.jpa.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    private JpaUserRepository repository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.toEntity(user);
        UserEntity save = repository.save(entity);
        return UserEntity.toDomain(save);
    }

    @Override
    public Optional<User> findById(UUID id) {
        Optional<UserEntity> entity = repository.findById(id.toString());
        return entity.map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByAddress(String address) {
        Optional<UserEntity> entity = repository.findByAddress(address);
        return entity.map(UserEntity::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id.toString());
    }
}
