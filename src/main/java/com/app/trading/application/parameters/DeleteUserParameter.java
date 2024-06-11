package com.app.trading.application.parameters;

import com.app.trading.domain.IParameter;

import java.util.UUID;

public record DeleteUserParameter(UUID userId) implements IParameter {
}
