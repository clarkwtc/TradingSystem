package com.app.trading.domain.events;

import com.app.trading.domain.Transaction;

public record TransactionEvent(Transaction transaction) implements IEvent {
}
