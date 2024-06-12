package com.prodapt.networkticketingapplicationproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prodapt.networkticketingapplicationproject.entities.ERole;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserEntityServiceImpl userEntityService;

    private UserEntity userEntity;
    private Role role;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");
        role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_USER);
        userEntity.setRole(role);
    }

    @Test
    void updaterole_UserFound_RoleUpdatedSuccessfully() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName(ERole.ROLE_ADMIN);
        
        String result = userEntityService.updaterole(1, newRole);

        assertEquals("Role Updated Successfully!!!", result);
        assertEquals(newRole, userEntity.getRole());
        verify(userRepository).save(userEntity);
    }

    @Test
    void getUserById_UserFound_ReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        UserEntity result = userEntityService.getUserById(1);

        assertEquals(userEntity, result);
    }

    @Test
    void getUserById_UserNotFound_ReturnNull() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        UserEntity result = userEntityService.getUserById(1);

        assertNull(result);
    }

    @Test
    void findByUsername_UserFound_ReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    void existsByUsername_UsernameExists_ReturnTrue() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertTrue(userEntityService.existsByUsername("testuser"));
    }

    @Test
    void existsByUsername_UsernameDoesNotExist_ReturnFalse() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);

        assertFalse(userEntityService.existsByUsername("testuser"));
    }

    @Test
    void existsByEmail_EmailExists_ReturnTrue() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertTrue(userEntityService.existsByEmail("test@example.com"));
    }

    @Test
    void existsByEmail_EmailDoesNotExist_ReturnFalse() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        assertFalse(userEntityService.existsByEmail("test@example.com"));
    }

    @Test
    void findByRole_UserWithRoleExists_ReturnUser() {
        when(userRepository.findByRole(ERole.ROLE_USER)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findByRole(ERole.ROLE_USER);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }


    @Test
    void addUserEntity_UserAddedSuccessfully_ReturnUser() {
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity result = userEntityService.addUserEntity(userEntity);

        assertEquals(userEntity, result);
    }

    @Test
    void getAll_ReturnAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        users.add(userEntity);
        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userEntityService.getAll();

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0), result.get(0));
    }
}
