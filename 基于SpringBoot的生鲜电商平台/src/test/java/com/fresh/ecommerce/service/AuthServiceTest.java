package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.AuthDTO;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.repository.UserRepository;
import com.fresh.ecommerce.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private AuthDTO.RegisterRequest registerRequest;
    private AuthDTO.LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new AuthDTO.RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("test@example.com");

        loginRequest = new AuthDTO.LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");
    }

    @Test
    void register_Success() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        
        User savedUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .email("test@example.com")
                .role(User.UserRole.USER)
                .status(User.UserStatus.ACTIVE)
                .build();
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken(any(), any(), any())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(), any())).thenReturn("refreshToken");

        AuthDTO.AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_UsernameExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(BusinessException.class, () -> authService.register(registerRequest));
    }

    @Test
    void login_Success() {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .role(User.UserRole.USER)
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(jwtUtil.generateToken(any(), any(), any())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(), any())).thenReturn("refreshToken");

        AuthDTO.AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
    }
}
