package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.OrderDTO;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.service.OrderService;
import com.fresh.ecommerce.service.PaymentService;
import com.fresh.ecommerce.service.UserService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping
    public ResultVO<OrderDTO.Response> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody OrderDTO.CreateRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.createOrder(user.getId(), request));
    }

    @GetMapping
    public ResultVO<Page<OrderDTO.Response>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Page<OrderDTO.Response> orders = orderService.getUserOrders(user.getId(), PageRequest.of(page, size));
        return ResultVO.success(orders);
    }

    @GetMapping("/{id}")
    public ResultVO<OrderDTO.Response> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.getOrderById(user.getId(), id));
    }

    @PostMapping("/{id}/cancel")
    public ResultVO<OrderDTO.Response> cancelOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.cancelOrder(user.getId(), id));
    }

    @PostMapping("/{id}/pay")
    public ResultVO<OrderDTO.Response> payOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody OrderDTO.PayRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.payOrder(user.getId(), id, request));
    }

    @PostMapping("/{id}/confirm")
    public ResultVO<OrderDTO.Response> confirmReceive(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.confirmReceive(user.getId(), id));
    }

    @PostMapping("/{id}/complete")
    public ResultVO<OrderDTO.Response> completeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(orderService.completeOrder(user.getId(), id));
    }
}
