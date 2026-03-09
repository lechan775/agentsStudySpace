package com.logistics.controller;

import com.logistics.entity.Vehicle;
import com.logistics.service.VehicleService;
import com.logistics.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @GetMapping
    public ResultVO<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResultVO.success(vehicles);
    }
    
    @GetMapping("/available")
    public ResultVO<List<Vehicle>> getAvailableVehicles() {
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        return ResultVO.success(vehicles);
    }
    
    @GetMapping("/{id}")
    public ResultVO<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResultVO.success(vehicle);
    }
    
    @PostMapping
    public ResultVO<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle created = vehicleService.createVehicle(vehicle);
        return ResultVO.success(created);
    }
    
    @PutMapping("/{id}")
    public ResultVO<Vehicle> updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle vehicle) {
        vehicle.setId(id);
        Vehicle updated = vehicleService.updateVehicle(vehicle);
        return ResultVO.success(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResultVO<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResultVO.success(null);
    }
}
