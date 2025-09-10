package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.JwtAuthenReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.JwtAuthenRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    UserSignUpRespDTO signUp(UserSignUpReqDTO request);
    JwtAuthenRespDTO login(JwtAuthenReqDTO request, HttpServletResponse response);
}
