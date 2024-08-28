package com.dmh;

import com.dmh.dao.AdminUserDao;
import com.dmh.entity.AdminUser;
import com.dmh.service.exception.LoginException;
import com.dmh.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AdminUserServiceImplTest {

    @Mock
    private AdminUserDao adminUserDao;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCheckLogin_ValidCredentials() {
        String username = "testUser";
        String password = "testPass";

        // Mocking the behavior of AdminUserDao
        when(adminUserDao.findByUsernameAndPassword(username, password)).thenReturn(new AdminUser());

        // Mocking the behavior of HttpServletRequest.getSession()
        when(request.getSession()).thenReturn(session);

        // Calling the method to be tested
        AdminUser result = adminUserService.checkLogin(request, username, password);

        // Asserting that the result is not null
        assertNotNull(result);
    }

    @Test
    void testCheckLogin_InvalidCredentials() {
        String username = "testUser";
        String password = "wrongPass";

        // Mocking the behavior of AdminUserDao
        when(adminUserDao.findByUsernameAndPassword(username, password)).thenReturn(null);

        // Mocking the behavior of HttpServletRequest.getSession()
        when(request.getSession()).thenReturn(session);

        // Calling the method to be tested and asserting that it throws an exception
        assertThrows(LoginException.class, () -> adminUserService.checkLogin(request, username, password));
    }
}
