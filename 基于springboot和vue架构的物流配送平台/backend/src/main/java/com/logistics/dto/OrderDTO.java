package com.logistics.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String orderNo;
    private Long userId;
    private String userName;
    private String senderName;
    private String senderPhone;
    private String senderAddress;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String goodsDescription;
    private Double weight;
    private Double price;
    private String status;
    private String createTime;
    private String deliverTime;
    private String completeTime;
}
