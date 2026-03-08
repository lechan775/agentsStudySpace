package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.AuthDTO;
import com.fresh.ecommerce.service.AuthService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResultVO<AuthDTO.AuthResponse> register(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        return ResultVO.success(authService.register(request));
    }

    @PostMapping("/login")
    public ResultVO<AuthDTO.AuthResponse> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        return ResultVO.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResultVO<AuthDTO.AuthResponse> refresh(@Valid @RequestBody AuthDTO.RefreshTokenRequest request) {
        return ResultVO.success(authService.refreshToken(request));
    }
}
