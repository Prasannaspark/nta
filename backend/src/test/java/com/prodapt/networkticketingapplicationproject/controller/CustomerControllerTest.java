package com.prodapt.networkticketingapplicationproject.controller;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.IssueType;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.GetCustomerTickets;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketRequest;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateRequest;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private CustomerController customerController;

    private Ticket dummyTicket;
    private UserEntity dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = new UserEntity();
        dummyUser.setId(1);

        dummyTicket = new Ticket();
        dummyTicket.setTicketId(1);
        dummyTicket.setUser(dummyUser);
    }

    @Test
    void testAddTicket_Success() {
        // Arrange
        TicketRequest request = new TicketRequest();
        request.setTitle("Test Ticket");
        request.setCustomerTier(CustomerTier.GOLD);
        request.setDescription("Test Description");
        request.setIssueType(IssueType.CONNECTIVITY);
        request.setPriority(Priority.HIGH);
        request.setSeverity(Severity.HIGH);
        request.setUserEntityId(dummyUser.getId());

        when(userEntityService.getUserById(dummyUser.getId())).thenReturn(dummyUser);
        when(ticketService.addTicket(any(Ticket.class))).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = customerController.addTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dummyTicket, response.getBody());
    }

    @Test
    void testAddTicket_UserNotFound() {
        // Arrange
        TicketRequest request = new TicketRequest();
        request.setUserEntityId(dummyUser.getId());

        when(userEntityService.getUserById(dummyUser.getId())).thenReturn(null);

        // Act
        ResponseEntity<Ticket> response = customerController.addTicket(request);

        // Assert
        assertNotEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateTicket_Success() throws TicketNotFoundException {
        // Arrange
        TicketUpdateRequest request = new TicketUpdateRequest();
        request.setTicketId(dummyTicket.getTicketId());
        request.setTitle("Updated Ticket");
        request.setCustomerTier(CustomerTier.GOLD);
        request.setDescription("Updated Description");
        request.setIssueType(IssueType.CONNECTIVITY);
        request.setPriority(Priority.LOW);
        request.setSeverity(Severity.HIGH);

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = customerController.updateTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dummyTicket, response.getBody());
    }

    @Test
    void testUpdateTicket_TicketNotFound() throws TicketNotFoundException {
        // Arrange
        TicketUpdateRequest request = new TicketUpdateRequest();
        request.setTicketId(dummyTicket.getTicketId());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> customerController.updateTicket(request));
    }

    @Test
    void testGetTicket_Success() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = customerController.getTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dummyTicket, response.getBody());
    }

    @Test
    void testGetTicket_TicketNotFound() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> customerController.getTicket(request));
    }

    @Test
    void testGetMyTicket_Success() throws TicketNotFoundException {
        // Arrange
        GetCustomerTickets request = new GetCustomerTickets();
        request.setId(dummyUser.getId());

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(dummyTicket);

        when(userEntityService.getUserById(dummyUser.getId())).thenReturn(dummyUser);
        when(ticketService.getByUserEntity(dummyUser)).thenReturn(tickets);

        // Act
        ResponseEntity<List<Ticket>> response = customerController.getMyTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testGetMyTicket_UserNotFound() throws TicketNotFoundException {
        // Arrange
        GetCustomerTickets request = new GetCustomerTickets();
        request.setId(dummyUser.getId());

        when(userEntityService.getUserById(dummyUser.getId())).thenReturn(null);

        // Act
        ResponseEntity<List<Ticket>> response = customerController.getMyTicket(request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
