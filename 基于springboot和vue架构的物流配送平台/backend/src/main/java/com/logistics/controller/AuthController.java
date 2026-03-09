package com.logistics.controller;

import com.logistics.dto.AuthResponse;
import com.logistics.dto.LoginRequest;
import com.logistics.dto.UserDTO;
import com.logistics.service.AuthService;
import com.logistics.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResultVO<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResultVO.success(response);
    }
    
    @PostMapping("/register")
    public ResultVO<AuthResponse> register(@RequestBody LoginRequest request) {
        AuthResponse response = authService.register(request);
        return ResultVO.success(response);
    }
    
    @GetMapping("/info")
    public ResultVO<UserDTO> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = authService.getUserInfo(username);
        return ResultVO.success(user);
    }
    
    @GetMapping("/couriers")
    public ResultVO<List<UserDTO>> getCouriers() {
        List<UserDTO> couriers = authService.getCouriers();
        return ResultVO.success(couriers);
    }
}
