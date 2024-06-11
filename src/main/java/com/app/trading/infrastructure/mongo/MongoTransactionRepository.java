package com.app.trading.infrastructure.mongo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface MongoTransactionRepository extends MongoRepository<TransactionCollection, UUID> {
    @Query("SELECT t FROM TransactionCollection t WHERE t.userId = :userId")
    List<TransactionCollection> findByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionCollection t WHERE t.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
