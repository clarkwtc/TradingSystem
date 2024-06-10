package com.app.trading.domain;

import com.app.trading.domain.events.IEvent;

public interface IUseCase<S extends IParameter, T extends IEvent> {
    T execute(S parameter);
}
