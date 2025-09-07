package com.hoangkhoi.springboot_ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseChecker {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void checkConnection() {
        try {
            jdbcTemplate.execute("SELECT 1");
            System.out.println("Connect to database successfully!");
        }
        catch (Exception e) {
            System.err.println("Failed to connect to database!: " + e.getMessage());
        }
    }
}