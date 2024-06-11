package com.app.trading.infrastructure;

import com.app.trading.domain.ITransactionRepository;
import com.app.trading.domain.Transaction;
import com.app.trading.infrastructure.mongo.MongoTransactionRepository;
import com.app.trading.infrastructure.mongo.TransactionCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository implements ITransactionRepository {
    @Autowired
    MongoTransactionRepository repository;

    @Override
    public List<Transaction> findByUserId(UUID userId) {
        List<TransactionCollection> collections = repository.findByUserId(userId.toString());
        return collections.stream().map(TransactionCollection::toDomain).collect(Collectors.toList());
    }

    @Override
    public Transaction save(UUID userId, Transaction transaction) {
        TransactionCollection collection = TransactionCollection.toEntity(userId, transaction);
        TransactionCollection save = repository.save(collection);
        return TransactionCollection.toDomain(save);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        repository.deleteByUserId(userId.toString());
    }
}
