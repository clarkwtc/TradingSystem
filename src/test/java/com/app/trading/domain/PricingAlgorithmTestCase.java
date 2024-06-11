package com.app.trading.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PricingAlgorithmTestCase {

    @Test
    public void afterThreeMinutes() {
        // Given
        PricingAlgorithm pricingAlgorithm = new PricingAlgorithm(new BigDecimal("100.0"), Currency.BTC, LocalDateTime.now().minusMinutes(3));
        // When
        // Then
        Assertions.assertEquals(new BigDecimal("460.0"), pricingAlgorithm.getLatestPrice());
    }

    @Test
    public void afterSixMinutes(){
        // Given
        PricingAlgorithm pricingAlgorithm = new PricingAlgorithm(new BigDecimal("100.0"), Currency.BTC, LocalDateTime.now().minusMinutes(6));
        // When
        // Then
        Assertions.assertEquals(new BigDecimal("100.0"), pricingAlgorithm.getLatestPrice());
    }
}
