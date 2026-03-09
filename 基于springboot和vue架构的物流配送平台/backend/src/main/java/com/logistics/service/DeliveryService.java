package com.logistics.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.logistics.entity.Delivery;
import com.logistics.entity.Order;
import com.logistics.entity.User;
import com.logistics.entity.Vehicle;
import com.logistics.repository.DeliveryRepository;
import com.logistics.repository.OrderRepository;
import com.logistics.repository.UserRepository;
import com.logistics.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    
    @Transactional
    public Delivery acceptDelivery(Long orderId, Long courierId, Long vehicleId) {
        Order order = orderRepository.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("订单状态不允许接单");
        }
        
        // Update order status
        order.setStatus("ACCEPTED");
        orderRepository.updateById(order);
        
        // Create delivery record
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setCourierId(courierId);
        delivery.setVehicleId(vehicleId);
        delivery.setStatus("ACCEPTED");
        delivery.setStartTime(LocalDateTime.now());
        
        deliveryRepository.insert(delivery);
        
        // Update vehicle status
        Vehicle vehicle = vehicleRepository.selectById(vehicleId);
        if (vehicle != null) {
            vehicle.setStatus("IN_USE");
            vehicleRepository.updateById(vehicle);
        }
        
        return delivery;
    }
    
    @Transactional
    public Delivery updateDeliveryStatus(Long deliveryId, String status) {
        Delivery delivery = deliveryRepository.selectById(deliveryId);
        if (delivery == null) {
            throw new RuntimeException("配送记录不存在");
        }
        
        delivery.setStatus(status);
        
        if ("COMPLETED".equals(status)) {
            delivery.setEndTime(LocalDateTime.now());
            
            // Update order status
            Order order = orderRepository.selectById(delivery.getOrderId());
            if (order != null) {
                order.setStatus("COMPLETED");
                order.setCompleteTime(LocalDateTime.now());
                orderRepository.updateById(order);
            }
            
            // Update vehicle status
            if (delivery.getVehicleId() != null) {
                Vehicle vehicle = vehicleRepository.selectById(delivery.getVehicleId());
                if (vehicle != null) {
                    vehicle.setStatus("IDLE");
                    vehicleRepository.updateById(vehicle);
                }
            }
        } else if ("DELIVERING".equals(status)) {
            // Update order status
            Order order = orderRepository.selectById(delivery.getOrderId());
            if (order != null) {
                order.setStatus("DELIVERING");
                orderRepository.updateById(order);
            }
        }
        
        deliveryRepository.updateById(delivery);
        
        return delivery;
    }
    
    public List<Delivery> getDeliveriesByCourier(Long courierId) {
        return deliveryRepository.selectList(
            new LambdaQueryWrapper<Delivery>()
                .eq(Delivery::getCourierId, courierId)
                .orderByDesc(Delivery::getCreateTime)
        );
    }
    
    public List<Object> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.selectList(
            new LambdaQueryWrapper<Delivery>()
                .orderByDesc(Delivery::getCreateTime)
        );
        
        List<Object> result = new ArrayList<>();
        for (Delivery d : deliveries) {
            Order order = orderRepository.selectById(d.getOrderId());
            User courier = d.getCourierId() != null ? userRepository.selectById(d.getCourierId()) : null;
            Vehicle vehicle = d.getVehicleId() != null ? vehicleRepository.selectById(d.getVehicleId()) : null;
            
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", d.getId());
            map.put("order", order);
            map.put("courier", courier != null ? courier.getUsername() : null);
            map.put("vehicleNumber", vehicle != null ? vehicle.getVehicleNumber() : null);
            map.put("status", d.getStatus());
            map.put("startTime", d.getStartTime());
            map.put("endTime", d.getEndTime());
            result.add(map);
        }
        
        return result;
    }
}
