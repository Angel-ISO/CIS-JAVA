package dg.project.UserManagement.infrastructure.service;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.domain.entities.User;
import dg.project.UserManagement.domain.repository.RoleRepository;
import dg.project.UserManagement.domain.repository.UserRepository;
import dg.project.UserManagement.infrastructure.utils.JwtUtils;
import dg.project.UserManagement.infrastructure.utils.RoleEnum;
import dg.project.UserManagement.presentation.dto.AuthCreateUserRequest;
import dg.project.UserManagement.presentation.dto.AuthResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private AuthCreateUserRequest validCreateUserRequest;
    private User validUser;
    private Role userRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validCreateUserRequest = new AuthCreateUserRequest("validUser", "ValidPassword123!", "Valid User", "validuser@example.com");
        validUser = new User("validUser", "Valid User", "validuser@example.com", "encodedPassword", new Date(), true, true, true, true, new HashSet<>());

        userRole = new Role(RoleEnum.USER, new HashSet<>());
        adminRole = new Role(RoleEnum.ADMIN, new HashSet<>());

        validUser.addRole(userRole);
        validUser.addRole(adminRole);
        userRole.getUsers().add(validUser);
        adminRole.getUsers().add(validUser);
    }


    @Test
    void testAddRoleToUser_Success() {
        UUID userId = validUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));
        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));

        // Primero quitamos el rol ADMIN para poder añadirlo después
        validUser.getRoles().remove(adminRole);

        userService.addRoleToUser(userId, "ADMIN");

        assertTrue(validUser.getRoles().contains(adminRole));
        verify(userRepository, times(1)).save(validUser);
    }

    @Test
    void testAddRoleToUser_RoleNotFound() {
        UUID userId = validUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));
        // Usamos un nombre de rol que existe pero no está mockeado
        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userService.addRoleToUser(userId, "ADMIN"));
        assertEquals("Role ADMIN does not exist.", thrown.getMessage());
    }



    @Test
    void testRemoveLastRoleShouldFail() {
        User singleRoleUser = new User("singleRoleUser", "Single Role", "single@example.com", "pass", new Date(), true, true, true, true, new HashSet<>());
        singleRoleUser.addRole(userRole);

        when(userRepository.findById(singleRoleUser.getId())).thenReturn(Optional.of(singleRoleUser));
        when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(userRole));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> userService.removeRoleFromUser(singleRoleUser.getId(), "USER"));

        assertEquals("A user must have at least one role.", thrown.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        UUID userId = validUser.getId();
        when(userRepository.findByname("validUser")).thenReturn(Optional.of(validUser));
        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));

        Authentication authentication = new UsernamePasswordAuthenticationToken("validUser", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        userService.deleteUser(userId, authentication);

        verify(userRepository, times(1)).deleteById(userId);
    }
}