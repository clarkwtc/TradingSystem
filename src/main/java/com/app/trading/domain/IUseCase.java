package com.app.trading.domain;

import com.app.trading.domain.events.IEvent;

public interface IUseCase<T extends IEvent, S extends IParameter> {
    T execute(S parameter);
}
