package com.prodapt.networkticketingapplicationproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.networkticketingapplicationproject.entities.ERole;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.UpdateUserRoleRequest;
import com.prodapt.networkticketingapplicationproject.service.RoleService;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserEntityService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testGetAllTickets() throws TicketNotFoundException {
        // Arrange
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAllTickets()).thenReturn(tickets);

        // Act
        ResponseEntity<List<Ticket>> response = adminController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<UserEntity> users = Arrays.asList(new UserEntity(), new UserEntity());
        when(userService.getAll()).thenReturn(users);

        // Act
        ResponseEntity<List<UserEntity>> response = adminController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testUpdateUserRole() throws RoleNotFoundException {
        // Arrange
        UpdateUserRoleRequest updateUserRoleRequest = new UpdateUserRoleRequest();
        updateUserRoleRequest.setId(1);
        updateUserRoleRequest.setRole(ERole.ROLE_ADMIN);

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_ADMIN);
        when(roleService.findRoleByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));

        // Act
        ResponseEntity<String> response = adminController.updateUserRole(updateUserRoleRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("Role Updated Successfully", response.getBody());
    }

    @Test
    void testGetAllTickets_TicketsNotFound() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getAllTickets()).thenThrow(new TicketNotFoundException());

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> adminController.getAll());
    }

}
