package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.UserMapper;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import com.hoangkhoi.springboot_ecommerce.model.User;
import com.hoangkhoi.springboot_ecommerce.repository.RoleRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserInfoMapper;
import com.hoangkhoi.springboot_ecommerce.repository.UserInfoRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserRepository;
import com.hoangkhoi.springboot_ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserInfoMapper userInfoMapper;

    @Override
    public UserSignUpRespDTO signUp(UserSignUpReqDTO request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getEmail())
            );
        }
        if(userInfoRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getPhone())
            );
        }

        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, RoleName.ROLE_USER.name()))
                );

        User user = userMapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        user.setUserInfos(
                List.of(userInfoRepository.save(userInfoMapper.toEntity(request)))
        );

        User savedUserModel = userRepository.save(user);
        UserSignUpRespDTO userSignUpResponse = userMapper.toDtoForSignUp(savedUserModel);
        return userSignUpResponse;
    }
}
