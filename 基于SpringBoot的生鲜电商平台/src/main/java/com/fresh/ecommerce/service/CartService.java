package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.CartItemDTO;
import com.fresh.ecommerce.entity.CartItem;
import com.fresh.ecommerce.entity.Product;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;

    public List<CartItemDTO.Response> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(CartItemDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartItemDTO.Response addToCart(Long userId, CartItemDTO.AddRequest request) {
        User user = userService.getUserById(userId);
        Product product = productService.getProducts(
                org.springframework.data.domain.Pageable.unpaged()
        ).stream().filter(p -> p.getId().equals(request.getProductId()))
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
                .orElseThrow(() -> new BusinessException("商品不存在"));

        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("库存不足");
        }

        // Check if item already exists in cart
        var existingItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new BusinessException("库存不足");
            }
            cartItem.setQuantity(newQuantity);
            return CartItemDTO.Response.fromEntity(cartItemRepository.save(cartItem));
        }

        CartItem cartItem = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        return CartItemDTO.Response.fromEntity(cartItemRepository.save(cartItem));
    }

    @Transactional
    public CartItemDTO.Response updateCartItemQuantity(Long userId, Long itemId, CartItemDTO.UpdateRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("购物车项不存在"));
        
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        
        if (cartItem.getProduct().getStock() < request.getQuantity()) {
            throw new BusinessException("库存不足");
        }
        
        cartItem.setQuantity(request.getQuantity());
        return CartItemDTO.Response.fromEntity(cartItemRepository.save(cartItem));
    }

    @Transactional
    public void removeCartItem(Long userId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("购物车项不存在"));
        
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }
}
