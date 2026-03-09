package com.logistics.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.logistics.entity.Vehicle;
import com.logistics.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.selectList(
            new LambdaQueryWrapper<Vehicle>()
                .orderByDesc(Vehicle::getCreateTime)
        );
    }
    
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.selectList(
            new LambdaQueryWrapper<Vehicle>()
                .eq(Vehicle::getStatus, "IDLE")
        );
    }
    
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.selectById(id);
    }
    
    public Vehicle createVehicle(Vehicle vehicle) {
        vehicle.setStatus("IDLE");
        vehicleRepository.insert(vehicle);
        return vehicle;
    }
    
    public Vehicle updateVehicle(Vehicle vehicle) {
        vehicleRepository.updateById(vehicle);
        return vehicle;
    }
    
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
