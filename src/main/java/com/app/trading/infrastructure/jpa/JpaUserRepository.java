package com.app.trading.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM UserEntity u WHERE u.address = :address")
    Optional<UserEntity> findByAddress(@Param("address") String address);
}
