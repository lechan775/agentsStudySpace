package com.logistics.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.dto.OrderDTO;
import com.logistics.entity.Order;
import com.logistics.entity.User;
import com.logistics.repository.OrderRepository;
import com.logistics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public OrderDTO createOrder(OrderDTO dto, String username) {
        User user = userRepository.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, username)
        );
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(user.getId());
        order.setSenderName(dto.getSenderName());
        order.setSenderPhone(dto.getSenderPhone());
        order.setSenderAddress(dto.getSenderAddress());
        order.setReceiverName(dto.getReceiverName());
        order.setReceiverPhone(dto.getReceiverPhone());
        order.setReceiverAddress(dto.getReceiverAddress());
        order.setGoodsDescription(dto.getGoodsDescription());
        order.setWeight(dto.getWeight());
        order.setPrice(dto.getPrice());
        order.setStatus("PENDING");
        
        orderRepository.insert(order);
        
        return toDTO(order);
    }
    
    public List<OrderDTO> getOrders(String status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        
        List<Order> orders = orderRepository.selectList(wrapper);
        
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public List<OrderDTO> getUserOrders(String username) {
        User user = userRepository.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, username)
        );
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        List<Order> orders = orderRepository.selectList(
            new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, user.getId())
                .orderByDesc(Order::getCreateTime)
        );
        
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        return toDTO(order);
    }
    
    @Transactional
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        order.setStatus(status);
        
        if ("DELIVERING".equals(status)) {
            order.setDeliverTime(LocalDateTime.now());
        } else if ("COMPLETED".equals(status)) {
            order.setCompleteTime(LocalDateTime.now());
        }
        
        orderRepository.updateById(order);
        
        return toDTO(order);
    }
    
    private String generateOrderNo() {
        return "LOG" + System.currentTimeMillis() + IdUtil.fastSimpleUUID().substring(0, 4).toUpperCase();
    }
    
    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNo(order.getOrderNo());
        dto.setUserId(order.getUserId());
        dto.setSenderName(order.getSenderName());
        dto.setSenderPhone(order.getSenderPhone());
        dto.setSenderAddress(order.getSenderAddress());
        dto.setReceiverName(order.getReceiverName());
        dto.setReceiverPhone(order.getReceiverPhone());
        dto.setReceiverAddress(order.getReceiverAddress());
        dto.setGoodsDescription(order.getGoodsDescription());
        dto.setWeight(order.getWeight());
        dto.setPrice(order.getPrice());
        dto.setStatus(order.getStatus());
        
        if (order.getCreateTime() != null) {
            dto.setCreateTime(order.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (order.getDeliverTime() != null) {
            dto.setDeliverTime(order.getDeliverTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (order.getCompleteTime() != null) {
            dto.setCompleteTime(order.getCompleteTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        
        return dto;
    }
}
