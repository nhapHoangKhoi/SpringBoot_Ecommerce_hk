package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.JwtAuthenReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.JwtAuthenRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "Sign up for user")
    public ResponseEntity<ApiResponse<UserSignUpRespDTO>> signUp(@RequestBody @Valid UserSignUpReqDTO request) {
        UserSignUpRespDTO userResponse = userService.signUp(request);

        ApiResponse<UserSignUpRespDTO> response = new ApiResponse<>(
                true,
                String.format("Sign up account successfully!"),
                userResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login into the system")
    public ResponseEntity<ApiResponse<JwtAuthenRespDTO>> login(
            @RequestBody @Valid JwtAuthenReqDTO request,
            HttpServletResponse httpResponse
    ) {
        JwtAuthenRespDTO authenResponse = userService.login(request, httpResponse);

        ApiResponse<JwtAuthenRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.LOGIN_SUCCESS_MESSAGE),
                authenResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout the system")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse httpResponse) {
        userService.logout(httpResponse);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.LOGOUT_SUCCESS_MESSAGE),
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Get user profile")
    public ResponseEntity<ApiResponse<UserRespDTO>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserRespDTO user = userService.getCurrentUser(userDetails);

        ApiResponse<UserRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_CURRENT_USER_SUCCESS),
                user
        );
        return ResponseEntity.ok(response);
    }
}
