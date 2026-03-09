package com.logistics.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logistics.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryRepository extends BaseMapper<Delivery> {
}
