package com.hoangkhoi.springboot_ecommerce.scheduler;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveScheduler {
    private static final Logger log = LoggerFactory.getLogger(KeepAliveScheduler.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.keep-alive.url:}")
    private String apiUrl;

    // send 1 GET request for every 14 minutes
    @Scheduled(cron = "0 */14 * * * *")
    public void sendGetRequest() {
        try {
            String response = restTemplate.getForObject(apiUrl, String.class);
            // System.out.println("GET request sent successfully. Response: " + response);
            log.info("GET request sent successfully. Response: " + response);
        } catch (Exception e) {
            log.error("Failed to send GET request: " + e.getMessage());
        }
    }
}
