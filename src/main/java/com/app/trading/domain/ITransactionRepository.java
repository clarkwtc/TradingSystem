package com.app.trading.domain;

import java.util.List;
import java.util.UUID;

public interface ITransactionRepository {
    List<Transaction> findByUserId(UUID userId);
    Transaction save(UUID userId, Transaction transaction);
    void deleteByUserId(UUID userId);
}
