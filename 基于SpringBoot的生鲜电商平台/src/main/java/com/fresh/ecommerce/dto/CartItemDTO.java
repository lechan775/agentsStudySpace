package com.fresh.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

public class CartItemDTO {

    @Data
    public static class AddRequest {
        @NotNull(message = "商品ID不能为空")
        private Long productId;

        @NotNull(message = "数量不能为空")
        @Positive(message = "数量必须为正数")
        private Integer quantity;
    }

    @Data
    public static class UpdateRequest {
        @NotNull(message = "数量不能为空")
        @Positive(message = "数量必须为正数")
        private Integer quantity;
    }

    @Data
    public static class Response {
        private Long id;
        private Long productId;
        private String productName;
        private String productImage;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;

        public static Response fromEntity(com.fresh.ecommerce.entity.CartItem cartItem) {
            Response response = new Response();
            response.setId(cartItem.getId());
            response.setProductId(cartItem.getProduct().getId());
            response.setProductName(cartItem.getProduct().getName());
            response.setProductImage(cartItem.getProduct().getImageUrl());
            response.setPrice(cartItem.getProduct().getPrice());
            response.setQuantity(cartItem.getQuantity());
            response.setSubtotal(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            return response;
        }
    }
}
