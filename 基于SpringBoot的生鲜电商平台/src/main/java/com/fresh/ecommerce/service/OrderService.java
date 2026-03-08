package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.CartItemDTO;
import com.fresh.ecommerce.dto.OrderDTO;
import com.fresh.ecommerce.entity.*;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.OrderItemRepository;
import com.fresh.ecommerce.repository.OrderRepository;
import com.fresh.ecommerce.util.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final AddressService addressService;

    @Transactional
    public OrderDTO.Response createOrder(Long userId, OrderDTO.CreateRequest request) {
        // Get user's cart items
        List<CartItemDTO.Response> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new BusinessException("购物车为空");
        }

        // Get shipping address
        Address address = addressService.getAddressById(userId, request.getAddressId());

        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDTO.Response cartItem : cartItems) {
            Product product = productService.getProducts(
                    org.springframework.data.domain.Pageable.unpaged()
            ).stream()
                    .filter(p -> p.getId().equals(cartItem.getProductId()))
                    .map(p -> {
                        Product entity = new Product();
                        entity.setId(p.getId());
                        entity.setName(p.getName());
                        entity.setPrice(p.getPrice());
                        entity.setImageUrl(p.getImageUrl());
                        entity.setStock(p.getStock());
                        return entity;
                    })
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("商品不存在: " + cartItem.getProductName()));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new BusinessException("商品库存不足: " + product.getName());
            }

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .productImage(product.getImageUrl())
                    .price(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .subtotal(subtotal)
                    .build();

            orderItems.add(orderItem);

            // Reduce stock
            productService.reduceStock(product.getId(), cartItem.getQuantity());
        }

        // Create order
        User user = new User();
        user.setId(userId);

        Order order = Order.builder()
                .orderNo(OrderNumberGenerator.generateOrderNo())
                .user(user)
                .shippingAddress(address)
                .totalAmount(totalAmount)
                .discount(BigDecimal.ZERO)
                .payAmount(totalAmount)
                .status(Order.OrderStatus.PENDING_PAYMENT)
                .remark(request.getRemark())
                .items(orderItems)
                .build();

        order = orderRepository.save(order);

        // Save order items
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        // Clear cart
        cartService.clearCart(userId);

        return OrderDTO.Response.fromEntity(order);
    }

    public Page<OrderDTO.Response> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(OrderDTO.Response::fromEntity);
    }

    public OrderDTO.Response getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
        return OrderDTO.Response.fromEntity(order);
    }

    @Transactional
    public OrderDTO.Response cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.PENDING_PAYMENT) {
            throw new BusinessException("订单状态不允许取消");
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        
        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = new Product();
            product.setId(item.getProduct().getId());
            product.setStock(item.getQuantity());
            // Note: Need to implement restoreStock method
        }

        return OrderDTO.Response.fromEntity(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO.Response payOrder(Long userId, Long orderId, OrderDTO.PayRequest request) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.PENDING_PAYMENT) {
            throw new BusinessException("订单状态不允许支付");
        }

        Order.PayMethod payMethod;
        try {
            payMethod = Order.PayMethod.valueOf(request.getPayMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("不支持的支付方式");
        }

        order.setPayMethod(payMethod);
        order.setPayOrderNo("PAY" + System.currentTimeMillis());
        order.setPayTime(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PAID);

        return OrderDTO.Response.fromEntity(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO.Response confirmReceive(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.SHIPPED) {
            throw new BusinessException("订单状态不允许确认收货");
        }

        order.setStatus(Order.OrderStatus.RECEIVED);
        order.setReceiveTime(LocalDateTime.now());

        return OrderDTO.Response.fromEntity(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO.Response completeOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.RECEIVED) {
            throw new BusinessException("订单状态不允许完成");
        }

        order.setStatus(Order.OrderStatus.COMPLETED);
        order.setCompleteTime(LocalDateTime.now());

        return OrderDTO.Response.fromEntity(orderRepository.save(order));
    }
}
