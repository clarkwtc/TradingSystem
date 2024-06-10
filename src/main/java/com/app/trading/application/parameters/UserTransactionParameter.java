package com.app.trading.application.parameters;

import com.app.trading.domain.IParameter;

import java.math.BigDecimal;
import java.util.UUID;

public record UserTransactionParameter(UUID userId, BigDecimal amount, String action) implements IParameter {
}
