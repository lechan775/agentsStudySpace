package com.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    private Long userId;
    
    private String senderName;
    private String senderPhone;
    private String senderAddress;
    
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    
    private String goodsDescription;
    private Double weight;
    private Double price;
    
    private String status;  // PENDING, ACCEPTED, DELIVERING, COMPLETED, CANCELLED
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    private LocalDateTime deliverTime;
    private LocalDateTime completeTime;
}
