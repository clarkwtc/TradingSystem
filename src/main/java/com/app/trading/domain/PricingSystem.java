package com.app.trading.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PricingSystem extends Thread {
    private BigDecimal price;
    private final Currency currency;
    private long timestamp;

    public PricingSystem(BigDecimal price, Currency currency) {
        this.price = price;
        this.currency = currency;
        this.timestamp = new Date().getTime();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void run() {
        while (true){
            waiting();
            adjustPrices();
        }
    }

    private void waiting(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isReachedPeriod(){
        LocalDateTime lastTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());

        boolean reachedPeriod = false;
        if (lastTime.plusMinutes(3).isBefore(LocalDateTime.now())){
            timestamp = new Date().getTime();
            reachedPeriod = true;
        }
        return reachedPeriod;
    }

    private void adjustPrices(){
        if (!isReachedPeriod()){
            increasePrices();
        }
        else{
            decreasePrices();
        }
    }

    public void increasePrices(){
        this.price = price.add(new BigDecimal(10));
    }

    public void decreasePrices(){
        this.price = price.subtract(new BigDecimal(10));
    }
}
