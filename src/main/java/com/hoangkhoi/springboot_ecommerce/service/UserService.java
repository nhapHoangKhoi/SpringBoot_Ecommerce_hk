package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.JwtAuthenReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.JwtAuthenRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserSignUpRespDTO signUp(UserSignUpReqDTO request);
    JwtAuthenRespDTO login(JwtAuthenReqDTO request, HttpServletResponse response);
    void logout(HttpServletResponse httpServletResponse);
    UserRespDTO getCurrentUser(UserDetails userDetails);
}
