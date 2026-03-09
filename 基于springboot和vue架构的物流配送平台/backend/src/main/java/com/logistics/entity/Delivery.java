package com.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_delivery")
public class Delivery {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long orderId;
    private Long courierId;
    private Long vehicleId;
    
    private String status;  // PENDING, ACCEPTED, DELIVERING, COMPLETED
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private String remark;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
