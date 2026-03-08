package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.AuthDTO;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.repository.UserRepository;
import com.fresh.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("用户名已存在");
        }
        
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("邮箱已被注册");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .nickname(request.getNickname() != null ? request.getNickname() : request.getUsername())
                .status(User.UserStatus.ACTIVE)
                .role(User.UserRole.USER)
                .build();

        user = userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return new AuthDTO.AuthResponse(accessToken, refreshToken, user.getId(), user.getUsername(), user.getRole().name());
    }

    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("用户不存在"));

        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return new AuthDTO.AuthResponse(accessToken, refreshToken, user.getId(), user.getUsername(), user.getRole().name());
    }

    public AuthDTO.AuthResponse refreshToken(AuthDTO.RefreshTokenRequest request) {
        if (!jwtUtil.validateToken(request.getRefreshToken())) {
            throw new BusinessException("刷新令牌无效");
        }

        Long userId = jwtUtil.getUserIdFromToken(request.getRefreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return new AuthDTO.AuthResponse(accessToken, refreshToken, user.getId(), user.getUsername(), user.getRole().name());
    }
}
