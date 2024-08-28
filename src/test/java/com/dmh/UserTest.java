package com.dmh;

import com.dmh.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User(1,"user", "password", "John Doe", "john@example.com", "123456789", "Address","CODE",1);
        assertNotNull(user);
        assertEquals(Optional.of(1), Optional.ofNullable(user.getId()));
        assertEquals("user", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertEquals("Address", user.getAddr());
    }

    // Other tests for getters, setters, hashCode, and toString methods can be added here
}
