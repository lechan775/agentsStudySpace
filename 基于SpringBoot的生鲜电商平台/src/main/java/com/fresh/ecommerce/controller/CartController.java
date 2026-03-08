package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.CartItemDTO;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.service.CartService;
import com.fresh.ecommerce.service.UserService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResultVO<List<CartItemDTO.Response>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(cartService.getCartItems(user.getId()));
    }

    @PostMapping("/items")
    public ResultVO<CartItemDTO.Response> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItemDTO.AddRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(cartService.addToCart(user.getId(), request));
    }

    @PutMapping("/items/{id}")
    public ResultVO<CartItemDTO.Response> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody CartItemDTO.UpdateRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(cartService.updateCartItemQuantity(user.getId(), id, request));
    }

    @DeleteMapping("/items/{id}")
    public ResultVO<Void> removeCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        cartService.removeCartItem(user.getId(), id);
        return ResultVO.success("删除成功");
    }

    @DeleteMapping("/clear")
    public ResultVO<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        cartService.clearCart(user.getId());
        return ResultVO.success("清空成功");
    }
}
