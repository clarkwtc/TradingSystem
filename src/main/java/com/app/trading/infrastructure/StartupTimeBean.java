package com.app.trading.infrastructure;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StartupTimeBean {

    private LocalDateTime startupTime;

    @PostConstruct
    public void init() {
        this.startupTime = LocalDateTime.now();
    }

    public LocalDateTime getStartupTime() {
        return startupTime;
    }
}
