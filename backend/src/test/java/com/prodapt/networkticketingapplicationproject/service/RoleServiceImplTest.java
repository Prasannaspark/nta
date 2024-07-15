package com.prodapt.networkticketingapplicationproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.networkticketingapplicationproject.entities.ERole;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.exceptionmessages.QueryMapper;
import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
import com.prodapt.networkticketingapplicationproject.repositories.RoleRepository;

public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindRoleByName_RoleFound() throws RoleNotFoundException {
        // Arrange
        ERole roleName = ERole.ROLE_USER;
        Role role = new Role();
        role.setName(roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.findRoleByName(roleName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getName());
    }

    @Test
    public void testFindRoleByName_RoleNotFound() {
        // Arrange
        ERole roleName = ERole.ROLE_USER;
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        // Act & Assert
        RoleNotFoundException thrown = assertThrows(
            RoleNotFoundException.class,
            () -> roleService.findRoleByName(roleName)
        );
        assertEquals(QueryMapper.ROLENOTFOUND, thrown.getMessage());
    }

    @Test
    public void testFindRoleById_RoleFound() throws RoleNotFoundException {
        // Arrange
        Integer roleId = 1;
        Role role = new Role();
        role.setId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.findRoleById(roleId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleId, result.get().getId());
    }

    @Test
    public void testFindRoleById_RoleNotFound() {
        // Arrange
        Integer roleId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        RoleNotFoundException thrown = assertThrows(
            RoleNotFoundException.class,
            () -> roleService.findRoleById(roleId)
        );
        assertEquals(QueryMapper.ROLENOTFOUND, thrown.getMessage());
    }
}
