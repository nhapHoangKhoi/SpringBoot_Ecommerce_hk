package com.hoangkhoi.springboot_ecommerce.exception.handler;

import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        System.out.println("-------------------------");
        logger.error(ExceptionMessages.UNEXPECTED_ERROR, ex.getMessage(), ex);
        System.out.println("-------------------------");

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        System.out.println("-------------------------");
        logger.error(ExceptionMessages.UNEXPECTED_ERROR, ex.getMessage(), ex);
        System.out.println("-------------------------");

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        System.out.println("-------------------------");
        logger.error(ExceptionMessages.UNEXPECTED_ERROR, ex.getMessage(), ex);
        System.out.println("-------------------------");

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Example 1:
        // "name: Category name cannot be blank!; description: Description name cannot be blank!"
        String combinedMessages = errors
                .entrySet()
                .stream()
                // .map(eachEntry -> eachEntry.getKey() + ": " + eachEntry.getValue())
                .map(eachEntry -> eachEntry.getValue())
                .collect(Collectors.joining("; "));

        // Example 2 (only take the very first message):
        // "name: Category name cannot be blank!"
        // String firstMessage = errors
        //         .entrySet()
        //         .stream()
        //         .findFirst()
        //         .map(eachEntry -> eachEntry.getKey() + ": " + eachEntry.getValue())
        //         .get();

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                false,
                combinedMessages,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // fallback for other uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        System.out.println("-------------------------");
        logger.error(ExceptionMessages.UNEXPECTED_ERROR, ex.getMessage(), ex);
        System.out.println("-------------------------");

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                "Unexpected error: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
