package com.logistics.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logistics.entity.Vehicle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleRepository extends BaseMapper<Vehicle> {
}
