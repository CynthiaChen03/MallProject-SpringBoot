package com.dmh;

import com.dmh.entity.AdminUser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AdminUserTest {

    @Test
    public void testAdminUserCreation() {
        AdminUser adminUser = new AdminUser(1, "admin", "password");
        assertNotNull(adminUser);
        assertEquals(Optional.of(1), Optional.ofNullable(adminUser.getId()));
        assertEquals("admin", adminUser.getUsername());
        assertEquals("password", adminUser.getPassword());
    }

    @Test
    public void testAdminUserEquality() {
        AdminUser adminUser1 = new AdminUser(1, "admin", "password");
        AdminUser adminUser2 = new AdminUser(1, "admin", "password");
        assertEquals(adminUser1, adminUser2);
    }

    // Other tests for getters, setters, hashCode, and toString methods can be added here
}
