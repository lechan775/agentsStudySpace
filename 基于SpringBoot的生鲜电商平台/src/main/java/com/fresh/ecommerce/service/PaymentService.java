package com.fresh.ecommerce.service;

import com.fresh.ecommerce.entity.Order;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    /**
     * Simulate payment process
     * In production, this would integrate with Alipay/WeChat Pay
     */
    @Transactional
    public Map<String, Object> createPayment(Long userId, Long orderId, String payMethod) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.PENDING_PAYMENT) {
            throw new BusinessException("订单状态不允许支付");
        }

        // Simulate payment params
        Map<String, Object> paymentParams = new HashMap<>();
        paymentParams.put("orderNo", order.getOrderNo());
        paymentParams.put("payAmount", order.getPayAmount());
        paymentParams.put("payMethod", payMethod);
        paymentParams.put("payOrderNo", "PAY" + System.currentTimeMillis());
        paymentParams.put("qrCode", "https://example.com/qr/" + System.currentTimeMillis());
        
        return paymentParams;
    }

    /**
     * Simulate payment callback
     * In production, this would be called by payment gateway
     */
    @Transactional
    public void handlePaymentCallback(String payOrderNo, String status) {
        Order order = orderRepository.findByPayOrderNo(payOrderNo)
                .orElseThrow(() -> new ResourceNotFoundException("支付订单不存在"));

        if ("SUCCESS".equals(status)) {
            order.setStatus(Order.OrderStatus.PAID);
            order.setPayTime(LocalDateTime.now());
        } else if ("FAILED".equals(status)) {
            order.setStatus(Order.OrderStatus.PENDING_PAYMENT);
        }

        orderRepository.save(order);
    }

    /**
     * Simulate refund process
     */
    @Transactional
    public Map<String, Object> refund(Long userId, Long orderId, String reason) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));

        if (order.getStatus() != Order.OrderStatus.PAID && 
            order.getStatus() != Order.OrderStatus.SHIPPED &&
            order.getStatus() != Order.OrderStatus.RECEIVED) {
            throw new BusinessException("订单状态不允许退款");
        }

        order.setStatus(Order.OrderStatus.REFUNDING);
        orderRepository.save(order);

        // Simulate refund result
        Map<String, Object> refundResult = new HashMap<>();
        refundResult.put("refundNo", "REF" + System.currentTimeMillis());
        refundResult.put("refundAmount", order.getPayAmount());
        refundResult.put("status", "PROCESSING");
        
        return refundResult;
    }

    @Transactional
    public void confirmRefund(String refundNo) {
        Order order = orderRepository.findByPayOrderNo(refundNo.replace("REF", "PAY"))
                .orElse(null);
        
        if (order != null && order.getStatus() == Order.OrderStatus.REFUNDING) {
            order.setStatus(Order.OrderStatus.REFUNDED);
            orderRepository.save(order);
        }
    }
}
