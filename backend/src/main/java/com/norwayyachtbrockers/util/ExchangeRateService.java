package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.dto.response.ExchangeRateResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExchangeRateService {
    private final RestTemplate restTemplate;
    private final Map<String, BigDecimal> rates = new ConcurrentHashMap<>();

    public ExchangeRateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void init() {
        updateExchangeRates();
    }

    @Scheduled(cron = "0 30 16 * * ?", zone = "CET")
    public void updateExchangeRates() {
        String url = "https://api.frankfurter.app/latest?to=USD,NOK,GBP";
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && response.getRates() != null) {
            rates.clear();
            rates.putAll(response.getRates());
            ZoneId zoneId = ZoneId.of("Europe/Berlin"); // or "Europe/Paris"
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            System.out.println("Current rates on : " + zonedDateTime + "  " + rates);
        }
    }

    public BigDecimal getRate(String currency) {
        return rates.get(currency);
    }
}

