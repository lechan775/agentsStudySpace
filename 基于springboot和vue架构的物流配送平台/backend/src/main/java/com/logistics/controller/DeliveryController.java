package com.logistics.controller;

import com.logistics.entity.Delivery;
import com.logistics.service.DeliveryService;
import com.logistics.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    
    private final DeliveryService deliveryService;
    
    @PostMapping("/accept")
    public ResultVO<Delivery> acceptDelivery(@RequestBody Map<String, Long> params) {
        Long orderId = params.get("orderId");
        Long courierId = params.get("courierId");
        Long vehicleId = params.get("vehicleId");
        
        Delivery delivery = deliveryService.acceptDelivery(orderId, courierId, vehicleId);
        return ResultVO.success(delivery);
    }
    
    @PutMapping("/{id}/status")
    public ResultVO<Delivery> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Delivery delivery = deliveryService.updateDeliveryStatus(id, status);
        return ResultVO.success(delivery);
    }
    
    @GetMapping
    public ResultVO<List<Object>> getAllDeliveries() {
        List<Object> deliveries = deliveryService.getAllDeliveries();
        return ResultVO.success(deliveries);
    }
}
