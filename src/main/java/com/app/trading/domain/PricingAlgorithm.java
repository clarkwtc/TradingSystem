package com.app.trading.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PricingAlgorithm {
    private final BigDecimal startingPrice;
    private final Currency currency;
    private final long startupTime;
    private static final BigDecimal DYNAMIC_PRICE = BigDecimal.valueOf(10.0);
    private static final int DYNAMIC_SECONDS = 5;
    private static final int MINUTE_PER = 60;
    private static final int SECOND_PER = 60;
    private static final int CONTINUOUS_MINUTES = 3;
    private static final BigDecimal MAX_PRICE = new BigDecimal(SECOND_PER * CONTINUOUS_MINUTES / DYNAMIC_SECONDS).multiply(DYNAMIC_PRICE);

    public PricingAlgorithm(BigDecimal startingPrice, Currency currency, LocalDateTime startupTime) {
        this.startingPrice = startingPrice;
        this.currency = currency;
        this.startupTime = toLong(startupTime);
    }

    public Currency getCurrency() {
        return currency;
    }

    private long toLong(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public BigDecimal getLatestPrice(){
        Duration duration = getDiffDuration();
        int minutes = (int) (duration.toMinutes() % MINUTE_PER);
        int seconds = (int) (duration.getSeconds() % SECOND_PER);
        int pricePeriod = minutes % (CONTINUOUS_MINUTES * 2);
        int pastTime = pricePeriod * SECOND_PER + seconds;
        return pricePeriod <= CONTINUOUS_MINUTES? increasePrices(pastTime): decreasePrices(pastTime);
    }

    private Duration getDiffDuration(){
        LocalDateTime lastTime = toLocalDateTime(startupTime);
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(lastTime, now);
    }

    private LocalDateTime toLocalDateTime(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    private BigDecimal increasePrices(int pastTime){
        BigDecimal changedPrice = new BigDecimal(pastTime / DYNAMIC_SECONDS).multiply(DYNAMIC_PRICE);
        return startingPrice.add(changedPrice);
    }

    private BigDecimal decreasePrices(int pastTime){
        BigDecimal changedPrice = new BigDecimal(pastTime / DYNAMIC_SECONDS).multiply(DYNAMIC_PRICE);
        return startingPrice.add(MAX_PRICE.multiply(new BigDecimal(2)).subtract(changedPrice));
    }
}
