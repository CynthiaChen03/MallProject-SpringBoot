package com.dmh;

import com.dmh.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductCreation() {
        Product product = new Product(1, "Product 1", 100.0, 90.0, "image.jpg", "Description", 0, 1, new Date());
        assertNotNull(product);
        assertEquals(Optional.of(1), Optional.ofNullable(product.getId()));
        assertEquals("Product 1", product.getTitle());
        assertEquals(Optional.of(100.0), Optional.ofNullable(product.getMarketPrice()));
        assertEquals(Optional.of(90.0), Optional.ofNullable(product.getShopPrice()));
        assertEquals("image.jpg", product.getImage());
        assertEquals("Description", product.getDesc());
        assertEquals(Optional.of(0), Optional.ofNullable(product.getIsHot()));
        assertEquals(Optional.of(1), Optional.ofNullable(product.getCsid()));
        assertNotNull(product.getPdate());
    }

    // Other tests for getters, setters, hashCode, and toString methods can be added here
}
