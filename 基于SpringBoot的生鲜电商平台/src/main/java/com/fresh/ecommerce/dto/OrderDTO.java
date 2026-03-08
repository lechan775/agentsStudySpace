package com.fresh.ecommerce.dto;

import com.fresh.ecommerce.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    @Data
    public static class CreateRequest {
        @NotNull(message = "收货地址ID不能为空")
        private Long addressId;

        private String remark;
    }

    @Data
    public static class PayRequest {
        @NotBlank(message = "支付方式不能为空")
        private String payMethod;
    }

    @Data
    public static class Response {
        private Long id;
        private String orderNo;
        private Long userId;
        private String username;
        private AddressDTO.Response shippingAddress;
        private BigDecimal totalAmount;
        private BigDecimal discount;
        private BigDecimal payAmount;
        private Order.OrderStatus status;
        private Order.PayMethod payMethod;
        private String payOrderNo;
        private LocalDateTime payTime;
        private LocalDateTime createTime;
        private LocalDateTime deliverTime;
        private LocalDateTime receiveTime;
        private LocalDateTime completeTime;
        private String remark;
        private List<OrderItemDTO> items;

        public static Response fromEntity(Order order) {
            Response response = new Response();
            response.setId(order.getId());
            response.setOrderNo(order.getOrderNo());
            response.setUserId(order.getUser().getId());
            response.setUsername(order.getUser().getUsername());
            
            AddressDTO.Response addressResponse = new AddressDTO.Response();
            addressResponse.setId(order.getShippingAddress().getId());
            addressResponse.setReceiverName(order.getShippingAddress().getReceiverName());
            addressResponse.setReceiverPhone(order.getShippingAddress().getReceiverPhone());
            addressResponse.setProvince(order.getShippingAddress().getProvince());
            addressResponse.setCity(order.getShippingAddress().getCity());
            addressResponse.setDistrict(order.getShippingAddress().getDistrict());
            addressResponse.setDetailAddress(order.getShippingAddress().getDetailAddress());
            addressResponse.setPostalCode(order.getShippingAddress().getPostalCode());
            addressResponse.setIsDefault(order.getShippingAddress().getIsDefault());
            response.setShippingAddress(addressResponse);
            
            response.setTotalAmount(order.getTotalAmount());
            response.setDiscount(order.getDiscount());
            response.setPayAmount(order.getPayAmount());
            response.setStatus(order.getStatus());
            response.setPayMethod(order.getPayMethod());
            response.setPayOrderNo(order.getPayOrderNo());
            response.setPayTime(order.getPayTime());
            response.setCreateTime(order.getCreateTime());
            response.setDeliverTime(order.getDeliverTime());
            response.setReceiveTime(order.getReceiveTime());
            response.setCompleteTime(order.getCompleteTime());
            response.setRemark(order.getRemark());
            
            if (order.getItems() != null) {
                response.setItems(order.getItems().stream()
                        .map(OrderItemDTO::fromEntity)
                        .collect(Collectors.toList()));
            }
            return response;
        }
    }

    @Data
    public static class OrderItemDTO {
        private Long id;
        private Long productId;
        private String productName;
        private String productImage;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;

        public static OrderItemDTO fromEntity(com.fresh.ecommerce.entity.OrderItem orderItem) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setId(orderItem.getId());
            dto.setProductId(orderItem.getProduct().getId());
            dto.setProductName(orderItem.getProductName());
            dto.setProductImage(orderItem.getProductImage());
            dto.setPrice(orderItem.getPrice());
            dto.setQuantity(orderItem.getQuantity());
            dto.setSubtotal(orderItem.getSubtotal());
            return dto;
        }
    }
}
