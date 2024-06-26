package com.app.trading.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository{
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByAddress(String Address);
    void deleteById(UUID id);
}
