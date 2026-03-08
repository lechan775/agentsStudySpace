package com.fresh.ecommerce.repository;

import com.fresh.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Optional<Order> findByOrderNo(String orderNo);

    Optional<Order> findByUserIdAndId(Long userId, Long orderId);

    Optional<Order> findByPayOrderNo(String payOrderNo);
}
