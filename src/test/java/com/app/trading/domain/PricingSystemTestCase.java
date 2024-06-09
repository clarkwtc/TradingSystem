package com.app.trading.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PricingSystemTestCase {
    PricingSystem pricingSystem;
    @BeforeEach
    void setUp() {
        this.pricingSystem = new PricingSystem(new BigDecimal(100), Currency.BTC);
    }

    @Test
    public void increasePrices() throws InterruptedException {
        // Given
        // When
        pricingSystem.start();
        Thread.sleep(6000);

        // Then
        Assertions.assertEquals(new BigDecimal(110), pricingSystem.getPrice());
    }

    @Test
    public void decreasePrices() throws InterruptedException {
        // Given
        // When
        pricingSystem.start();
        Thread.sleep(6000);

        // Then
        Assertions.assertEquals(new BigDecimal(100), pricingSystem.getPrice());
    }
}
