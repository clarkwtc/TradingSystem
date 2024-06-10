package com.app.trading.domain.events;

import com.app.trading.domain.User;

public record CreateUserEvent(User user) implements IEvent {
}
