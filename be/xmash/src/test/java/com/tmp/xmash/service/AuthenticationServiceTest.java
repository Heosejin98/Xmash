package com.tmp.xmash.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.model.LoginRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    private final String userId = "ho9";
    private final String password = "qwer123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String encodedPassword = "$2a$10$encodedValue"; // Dummy encoded value
        AppUser appUser = new AppUser(null, userId, "test@naver.com", "김진", encodedPassword, "남");
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(appUser));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
    }

    @Test
    void testLoginSuccess() {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest(userId, password);

        // WHEN
        boolean result = authenticationService.login(loginRequest);

        // THEN
        assertTrue(result);
    }

    @Test
    void testLoginFailInvalidPassword() {
        // GIVEN
        String userId = "ho9";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(userId, password);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> authenticationService.login(loginRequest));
    }

    @Test
    void testLoginFailUserNotFound() {
        // GIVEN
        String userId = "nonexistent";
        String password = "qwer1234";
        LoginRequest loginRequest = new LoginRequest(userId, password);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> authenticationService.login(loginRequest));
    }
}