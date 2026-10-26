package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/server-heartbeat")
public class KeepAliveController {
    // this endpoint used for KeepAliveScheduler only
    // no other purpose
    @GetMapping
    @Operation(summary = "Send request to keep server alive")
    public ResponseEntity<ApiResponse<Void>> checkServer() {
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Server is running",
                null
        );

        return ResponseEntity.ok(response);
    }
}
