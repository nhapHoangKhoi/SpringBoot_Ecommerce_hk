package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.JwtAuthenReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.JwtAuthenRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.UserMapper;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import com.hoangkhoi.springboot_ecommerce.model.User;
import com.hoangkhoi.springboot_ecommerce.repository.RoleRepository;
import com.hoangkhoi.springboot_ecommerce.mapper.UserInfoMapper;
import com.hoangkhoi.springboot_ecommerce.repository.UserInfoRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserRepository;
import com.hoangkhoi.springboot_ecommerce.security.JwtTokenProvider;
import com.hoangkhoi.springboot_ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserInfoMapper userInfoMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Override
    public JwtAuthenRespDTO login(JwtAuthenReqDTO request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        ResponseCookie cookie = createCookie(token, 24 * 60 * 60);

        // set cookie in response header
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        JwtAuthenRespDTO jwtAuthenResponse = new JwtAuthenRespDTO();
        jwtAuthenResponse.setAccessToken(token);

        return jwtAuthenResponse;
    }

    @Override
    public void logout(HttpServletResponse response) {
        // Create an expired cookie
        ResponseCookie cookie = createCookie("", 0);

        // Add the cookie to the response
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public UserRespDTO getCurrentUser(UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, email))
                );

        System.out.println(">>> user.getCreatedAt()" + user.getCreatedAt());

        UserRespDTO userResponse = userMapper.toDto(user);
        return userResponse;
    }

    @Override
    public UserRespDTO createUser(UserReqDTO request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getEmail())
            );
        }

        User user = userMapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));
        // set role for the user
        Set<Role> roles = request.getRoleIds()
                .stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(ExceptionMessages.NOT_FOUND, roleId)
                        ))
                )
                .collect(Collectors.toSet());

        user.setRoles(roles);

        UserRespDTO userResponse = userMapper.toDto(userRepository.save(user));
        return userResponse;
    }

    @Override
    public List<UserRespDTO> getAllUsers() {
        List<UserRespDTO> users = userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return users;
    }

    //----- Helper methods -----//
    private ResponseCookie createCookie(String token, long maxAge) {
        return ResponseCookie.from("spring_token", token)
                .httpOnly(true) // only Server is allowed to access this cookie
                .secure(false)  // true: website https, false: website not https
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
    }
    //----- End helper methods -----//
}
