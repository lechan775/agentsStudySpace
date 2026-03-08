package com.fresh.ecommerce.dto;

import com.fresh.ecommerce.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {

    @Data
    public static class CreateRequest {
        @NotBlank(message = "商品名称不能为空")
        private String name;

        private String description;

        @NotNull(message = "价格不能为空")
        @Positive(message = "价格必须为正数")
        private BigDecimal price;

        @NotNull(message = "库存不能为空")
        @Positive(message = "库存必须为正数")
        private Integer stock;

        private String unit;
        private String imageUrl;
        private Long categoryId;
        private LocalDateTime productionDate;
        private LocalDateTime expiryDate;
    }

    @Data
    public static class UpdateRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String unit;
        private String imageUrl;
        private Long categoryId;
        private LocalDateTime productionDate;
        private LocalDateTime expiryDate;
        private Boolean isActive;
    }

    @Data
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String unit;
        private String imageUrl;
        private Long categoryId;
        private String categoryName;
        private Product.FreshnessLevel freshness;
        private LocalDateTime productionDate;
        private LocalDateTime expiryDate;
        private Boolean isActive;
        private Integer salesCount;
        private LocalDateTime createTime;

        public static Response fromEntity(Product product) {
            Response response = new Response();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setUnit(product.getUnit());
            response.setImageUrl(product.getImageUrl());
            if (product.getCategory() != null) {
                response.setCategoryId(product.getCategory().getId());
                response.setCategoryName(product.getCategory().getName());
            }
            response.setFreshness(product.getFreshness());
            response.setProductionDate(product.getProductionDate());
            response.setExpiryDate(product.getExpiryDate());
            response.setIsActive(product.getIsActive());
            response.setSalesCount(product.getSalesCount());
            response.setCreateTime(product.getCreateTime());
            return response;
        }
    }
}
