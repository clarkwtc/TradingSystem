package com.app.trading.application.parameters;

import com.app.trading.domain.IParameter;

public record CreateUseCaseParameter(String name, String email, String address) implements IParameter {
}
