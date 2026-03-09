package com.logistics.controller;

import com.logistics.dto.OrderDTO;
import com.logistics.service.OrderService;
import com.logistics.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    public ResultVO<OrderDTO> createOrder(
            @RequestBody OrderDTO orderDTO,
            Authentication authentication) {
        String username = authentication.getName();
        OrderDTO created = orderService.createOrder(orderDTO, username);
        return ResultVO.success(created);
    }
    
    @GetMapping
    public ResultVO<List<OrderDTO>> getOrders(
            @RequestParam(required = false) String status) {
        List<OrderDTO> orders = orderService.getOrders(status);
        return ResultVO.success(orders);
    }
    
    @GetMapping("/my")
    public ResultVO<List<OrderDTO>> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        List<OrderDTO> orders = orderService.getUserOrders(username);
        return ResultVO.success(orders);
    }
    
    @GetMapping("/{id}")
    public ResultVO<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResultVO.success(order);
    }
    
    @PutMapping("/{id}/status")
    public ResultVO<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        OrderDTO updated = orderService.updateOrderStatus(id, status);
        return ResultVO.success(updated);
    }
}
