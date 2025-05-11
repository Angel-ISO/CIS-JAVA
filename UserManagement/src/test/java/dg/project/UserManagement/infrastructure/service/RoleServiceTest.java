package dg.project.UserManagement.infrastructure.service;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.domain.repository.RoleRepository;
import dg.project.UserManagement.infrastructure.utils.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setId(UUID.randomUUID());
        role.setName(RoleEnum.ADMIN);
    }

    @Test
    void testCreateRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        roleService.create(role);

        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testGetRoleById() {
        UUID roleId = role.getId();
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleService.getById(roleId);

        assertTrue(foundRole.isPresent());
        assertEquals(roleId, foundRole.get().getId());
    }

    @Test
    void testGetRoleById_NotFound() {
        UUID roleId = UUID.randomUUID();
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> foundRole = roleService.getById(roleId);

        assertFalse(foundRole.isPresent());
    }

    @Test
    void testUpdateRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        role.setName(RoleEnum.USER);

        roleService.update(role);

        // Assert
        verify(roleRepository, times(1)).save(role);

        assertEquals(RoleEnum.USER, role.getName());
    }

    @Test
    void testDeleteRole() {
        UUID roleId = role.getId();
        doNothing().when(roleRepository).deleteById(roleId);

        roleService.delete(roleId);

        verify(roleRepository, times(1)).deleteById(roleId);
    }
}