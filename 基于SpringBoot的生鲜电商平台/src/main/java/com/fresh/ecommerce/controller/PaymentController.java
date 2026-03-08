package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.service.PaymentService;
import com.fresh.ecommerce.service.UserService;
import com.fresh.ecommerce.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping("/{orderId}")
    public ResultVO<Map<String, Object>> createPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @RequestParam String payMethod) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(paymentService.createPayment(user.getId(), orderId, payMethod));
    }

    @PostMapping("/callback/{payMethod}")
    public ResultVO<Void> paymentCallback(
            @PathVariable String payMethod,
            @RequestParam String payOrderNo,
            @RequestParam String status) {
        paymentService.handlePaymentCallback(payOrderNo, status);
        return ResultVO.success("回调处理成功");
    }

    @PostMapping("/refund/{orderId}")
    public ResultVO<Map<String, Object>> refund(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @RequestParam String reason) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(paymentService.refund(user.getId(), orderId, reason));
    }

    @PostMapping("/refund/confirm")
    public ResultVO<Void> confirmRefund(@RequestParam String refundNo) {
        paymentService.confirmRefund(refundNo);
        return ResultVO.success("退款确认成功");
    }
}
