package com.dmh;
import com.dmh.entity.User;
import com.dmh.service.UserService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService = mock(UserService.class);

    @Test
    public void testCheckLoginSuccess() {
        String username = "testUser";
        String password = "testPass";

        when(userService.checkLogin(username, password)).thenReturn(new User());

        boolean result = userService.checkLogin(username, password) != null;

        assertTrue(result);
        verify(userService).checkLogin(username, password);
    }

    @Test
    public void testCheckLoginFailure() {
        String username = "testUser";
        String password = "wrongPass";

        when(userService.checkLogin(username, password)).thenReturn(null);

        boolean result = userService.checkLogin(username, password) != null;

        assertFalse(result);
        verify(userService).checkLogin(username, password);
    }
}