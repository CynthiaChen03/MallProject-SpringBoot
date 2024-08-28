package com.dmh;

import com.dmh.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    public void testOrderItemCreation() {
        OrderItem orderItem = new OrderItem(1, 1, 1, 2, 180.0);
        assertNotNull(orderItem);
        assertEquals(Optional.of(1), Optional.ofNullable(orderItem.getId()));
        assertEquals(Optional.of(1), Optional.ofNullable(orderItem.getOrderId()));
        assertEquals(Optional.of(1), Optional.ofNullable(orderItem.getProductId()));
        assertEquals(Optional.of(2), Optional.ofNullable(orderItem.getCount()));
        assertEquals(Optional.of(180.0), Optional.ofNullable(orderItem.getSubTotal()));
    }

    // Other tests for getters, setters, hashCode, and toString methods can be added here
}