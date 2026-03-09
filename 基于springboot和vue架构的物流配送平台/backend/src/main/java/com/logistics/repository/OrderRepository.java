package com.logistics.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logistics.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository extends BaseMapper<Order> {
}
