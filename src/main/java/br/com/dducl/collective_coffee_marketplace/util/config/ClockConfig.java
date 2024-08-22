package br.com.dducl.collective_coffee_marketplace.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

    @Bean
    public Clock getClock() {
        return Clock.system(ZoneId.of("America/Sao_Paulo"));
    }
}
