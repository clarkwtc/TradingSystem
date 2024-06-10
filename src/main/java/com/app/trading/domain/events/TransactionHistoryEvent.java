package com.app.trading.domain.events;

import com.app.trading.domain.Transaction;

import java.util.List;

public record TransactionHistoryEvent(List<Transaction> transactions) implements IEvent{
}
