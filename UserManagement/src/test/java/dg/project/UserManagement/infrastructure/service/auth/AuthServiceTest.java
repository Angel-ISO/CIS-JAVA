package dg.project.UserManagement.infrastructure.service.auth;

import dg.project.UserManagement.infrastructure.utils.JwtUtils;
import dg.project.UserManagement.presentation.dto.AuthLoginRequest;
import dg.project.UserManagement.presentation.dto.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private AuthLoginRequest validLoginRequest;
    private AuthLoginRequest invalidLoginRequest;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validLoginRequest = new AuthLoginRequest("validUser", "validPassword");
        invalidLoginRequest = new AuthLoginRequest("invalidUser", "invalidPassword");

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("validUser");
        when(userDetails.getPassword()).thenReturn("validPasswordHash");
    }

    @Test
    void testLoginUser_Success() {
        when(userDetailsService.loadUserByUsername("validUser")).thenReturn(userDetails);
        when(passwordEncoder.matches("validPassword", "validPasswordHash")).thenReturn(true);
        when(jwtUtils.createToken(any(Authentication.class))).thenReturn("mockedAccessToken");

        AuthResponse response = authService.loginUser(validLoginRequest);

        assertTrue(response.status());
        assertEquals("User logged successfully", response.message());
        assertNotNull(response.jwt());
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        when(userDetailsService.loadUserByUsername("invalidUser")).thenThrow(new BadCredentialsException("Invalid credentials"));

        AuthResponse response = authService.loginUser(invalidLoginRequest);

        assertFalse(response.status());
        assertEquals("Invalid credentials", response.message());
        assertNull(response.jwt());
    }

    @Test
    void testLoginUser_Exception() {
        when(userDetailsService.loadUserByUsername("validUser")).thenReturn(userDetails);
        when(passwordEncoder.matches("validPassword", "validPasswordHash")).thenThrow(new RuntimeException("Unexpected error"));

        AuthResponse response = authService.loginUser(validLoginRequest);

        assertFalse(response.status());
        assertEquals("An error occurred during login", response.message());
        assertNull(response.jwt());
    }
}