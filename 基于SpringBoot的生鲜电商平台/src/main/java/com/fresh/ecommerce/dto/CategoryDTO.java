package com.fresh.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CategoryDTO {

    @Data
    public static class CreateRequest {
        @NotBlank(message = "分类名称不能为空")
        private String name;
        private String description;
        private String iconUrl;
        private Integer sortOrder;
    }

    @Data
    public static class UpdateRequest {
        private String name;
        private String description;
        private String iconUrl;
        private Integer sortOrder;
    }

    @Data
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private String iconUrl;
        private Integer sortOrder;
        private Integer productCount;
    }
}
