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
import com.hoangkhoi.springboot_ecommerce.model.UserInfo;
import com.hoangkhoi.springboot_ecommerce.repository.RoleRepository;
import com.hoangkhoi.springboot_ecommerce.mapper.UserInfoMapper;
import com.hoangkhoi.springboot_ecommerce.repository.UserInfoRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserRepository;
import com.hoangkhoi.springboot_ecommerce.security.JwtTokenProvider;
import com.hoangkhoi.springboot_ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
import java.util.UUID;
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

    private final CacheManager cacheManager;

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

        // save user first so it has an ID
        User savedUser = userRepository.save(user);

        // create userInfo and link back to user
        UserInfo userInfo = userInfoMapper.toEntity(request);
        userInfo.setUser(savedUser); // important
        userInfoRepository.save(userInfo);

        // optionally set the relationship for returning DTO
        savedUser.setUserInfos(List.of(userInfo));

        UserSignUpRespDTO userSignUpResponse = userMapper.toDtoForSignUp(savedUser);
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

    @Override
    public UserRespDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Id " + ExceptionMessages.NOT_FOUND, id))
                );

        UserRespDTO userResponse = userMapper.toDto(user);
        return userResponse;
    }

    @Override
    public UserRespDTO updateUser(UUID id, UserReqDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, id))
                );
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getEmail())
            );
        }

        // check if role exist and get corresponding roles
        Set<Role> roles = request.getRoleIds()
                .stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("Role id " + ExceptionMessages.NOT_FOUND, roleId)))
                )
                .collect(Collectors.toSet());

        // evict cache entry manually
        removeUserFromCache(user.getEmail());

        userMapper.updateUserFromDto(request, user);

        // update encoded password
        if(request.getPassword() != null) {
            user.setPassword(encoder.encode(request.getPassword()));
        }

        // update role
        user.setRoles(roles);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUserSoft(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, id))
                );

        // evict cache entry manually
        removeUserFromCache(user.getEmail());

        user.setDeleted(true);
        userRepository.save(user);
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

    private void removeUserFromCache(String email) {
        Cache usersCache = cacheManager.getCache("users");
        if(usersCache != null) {
            usersCache.evict(email);
        }
    }
    //----- End helper methods -----//
}
