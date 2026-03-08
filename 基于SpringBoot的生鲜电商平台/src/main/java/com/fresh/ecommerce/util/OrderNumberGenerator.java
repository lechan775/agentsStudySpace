package com.fresh.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {

    private static final AtomicInteger sequence = new AtomicInteger(1000);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String generateOrderNo() {
        String dateTime = LocalDateTime.now().format(DATE_FORMAT);
        int seq = sequence.getAndIncrement();
        if (seq > 9999) {
            sequence.set(1000);
        }
        return "ORD" + dateTime + String.format("%04d", seq);
    }
}
