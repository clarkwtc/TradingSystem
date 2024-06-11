package com.app.trading.infrastructure;

import com.app.trading.domain.ITransactionRepository;
import com.app.trading.domain.Transaction;
import com.app.trading.infrastructure.jpa.JpaTransactionRepository;
import com.app.trading.infrastructure.jpa.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository implements ITransactionRepository {
    @Autowired
    JpaTransactionRepository repository;

    @Override
    public List<Transaction> findByUserId(UUID userId) {
        List<TransactionEntity> transactionEntities = repository.findByUserId(userId.toString());
        return transactionEntities.stream().map(TransactionEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Transaction save(UUID userId, Transaction transaction) {
        TransactionEntity entity = TransactionEntity.toEntity(userId, transaction);
        TransactionEntity save = repository.save(entity);
        return TransactionEntity.toDomain(save);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        repository.deleteByUserId(userId.toString());
    }
}
