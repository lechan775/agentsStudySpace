package com.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_vehicle")
public class Vehicle {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String vehicleNumber;
    private String brand;
    private String model;
    
    private String status;  // IDLE, IN_USE, MAINTENANCE
    
    private Double maxLoad;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
