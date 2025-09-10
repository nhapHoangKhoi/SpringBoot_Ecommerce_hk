package com.hoangkhoi.springboot_ecommerce.security;

public class SecurityApiConstants {
    public static final String[] PUBLIC_API = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api/v*/users/login",
            "/api/v*/users/signup",
            "/api/v*/users/logout",
    };

    public static final String[] ADMIN_WRITE_API = {
            "/api/v*/categories/**",
            "/api/v*/products/**"
    };
}
