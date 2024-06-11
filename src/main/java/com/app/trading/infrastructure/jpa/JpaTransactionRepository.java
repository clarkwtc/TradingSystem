package com.app.trading.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.userId = :userId")
    List<TransactionEntity> findByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionEntity t WHERE t.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
