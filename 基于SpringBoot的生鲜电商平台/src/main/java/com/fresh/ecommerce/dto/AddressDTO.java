package com.fresh.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class AddressDTO {

    @Data
    public static class CreateRequest {
        @NotBlank(message = "收货人姓名不能为空")
        private String receiverName;

        @NotBlank(message = "收货人电话不能为空")
        private String receiverPhone;

        @NotBlank(message = "省份不能为空")
        private String province;

        @NotBlank(message = "城市不能为空")
        private String city;

        @NotBlank(message = "区县不能为空")
        private String district;

        @NotBlank(message = "详细地址不能为空")
        private String detailAddress;

        private String postalCode;
        private Boolean isDefault = false;
    }

    @Data
    public static class UpdateRequest {
        private String receiverName;
        private String receiverPhone;
        private String province;
        private String city;
        private String district;
        private String detailAddress;
        private String postalCode;
        private Boolean isDefault;
    }

    @Data
    public static class Response {
        private Long id;
        private String receiverName;
        private String receiverPhone;
        private String province;
        private String city;
        private String district;
        private String detailAddress;
        private String postalCode;
        private Boolean isDefault;
    }
}
