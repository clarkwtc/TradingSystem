package com.app.trading.domain.events;

import com.app.trading.domain.User;

public record DeleteUserEvent(User user) implements IEvent{
}
