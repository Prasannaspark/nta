package com.prodapt.networkticketingapplicationproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.networkticketingapplicationproject.entities.ERole;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.repositories.UserRepository;

public class UserEntityServiceImplTest {

    @InjectMocks
    private UserEntityServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateRoleSuccess() {
        String username = "testuser";
        Role role = new Role(1, ERole.ROLE_SYSTEM_ADMIN);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        String result = userService.updaterole(username, role);

        assertEquals("Role Updated Successfully!!!", result);
        assertEquals(role, userEntity.getRole());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateRoleFailure_UserNotFound() {
        String username = "nonexistentuser";
        Role role = new Role(1, ERole.ROLE_SYSTEM_ADMIN);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        String result = userService.updaterole(username, role);

        assertEquals("Role Update Unsuccessfull", result); 
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testGetUserByIdSuccess() {
        Integer userId = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.getUserById(userId);

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserByIdFailure_UserNotFound() {
        Integer userId = 2;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserEntity result = userService.getUserById(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByUsernameSuccess() {
        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindByUsernameFailure_UserNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.findByUsername(username);

        assertTrue(result == null || !result.isPresent());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testExistsByUsername() {
        String username = "testuser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean result = userService.existsByUsername(username);

        assertTrue(result);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void testExistsByEmail() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.existsByEmail(email);

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testFindByRoleSuccess() {
        ERole role = ERole.ROLE_SYSTEM_ADMIN;
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(new Role(1, role));

        when(userRepository.findByRole(role)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userService.findByRole(role);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
        verify(userRepository, times(1)).findByRole(role);
    }

    @Test
    void testFindByRoleFailure_UserNotFound() {
        ERole role = ERole.ROLE_USER;

        when(userRepository.findByRole(role)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.findByRole(role);

        assertTrue(result == null || result.isEmpty());  
        verify(userRepository, times(1)).findByRole(role);
    } 

    @Test
    void testAddUserEntity() {
        UserEntity userEntity = new UserEntity();

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity result = userService.addUserEntity(userEntity);

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testGetAll() {
        List<UserEntity> users = Arrays.asList(new UserEntity(), new UserEntity());

        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userService.getAll();

        assertEquals(users.size(), result.size());
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }
}
