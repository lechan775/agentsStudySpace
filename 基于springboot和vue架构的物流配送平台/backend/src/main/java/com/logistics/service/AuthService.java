package com.logistics.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.dto.AuthResponse;
import com.logistics.dto.LoginRequest;
import com.logistics.dto.UserDTO;
import com.logistics.entity.User;
import com.logistics.repository.UserRepository;
import com.logistics.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
        );
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        String hashedPassword = SecureUtil.md5(request.getPassword());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("账户已被禁用");
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        
        return new AuthResponse(token, toDTO(user));
    }
    
    public AuthResponse register(LoginRequest request) {
        User existUser = userRepository.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
        );
        
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(SecureUtil.md5(request.getPassword()));
        user.setRole("USER");
        user.setStatus("ACTIVE");
        
        userRepository.insert(user);
        
        String token = jwtUtil.generateToken(user.getUsername());
        
        return new AuthResponse(token, toDTO(user));
    }
    
    public UserDTO getUserInfo(String username) {
        User user = userRepository.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        return toDTO(user);
    }
    
    public List<UserDTO> getCouriers() {
        List<User> couriers = userRepository.selectList(
            new LambdaQueryWrapper<User>()
                .eq(User::getRole, "COURIER")
                .eq(User::getStatus, "ACTIVE")
        );
        
        return couriers.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRealName(user.getRealName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
}
