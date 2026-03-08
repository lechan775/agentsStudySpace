package com.fresh.ecommerce.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class OrderNumberGeneratorTest {

    @Test
    void generateOrderNo_ShouldReturnUniqueOrderNumbers() {
        String orderNo1 = OrderNumberGenerator.generateOrderNo();
        String orderNo2 = OrderNumberGenerator.generateOrderNo();

        assertNotNull(orderNo1);
        assertNotNull(orderNo2);
        assertTrue(orderNo1.startsWith("ORD"));
        assertTrue(orderNo2.startsWith("ORD"));
    }

    @Test
    void generateOrderNo_ShouldHaveCorrectFormat() {
        String orderNo = OrderNumberGenerator.generateOrderNo();

        assertTrue(orderNo.length() > 10);
        assertTrue(orderNo.startsWith("ORD"));
    }
}
