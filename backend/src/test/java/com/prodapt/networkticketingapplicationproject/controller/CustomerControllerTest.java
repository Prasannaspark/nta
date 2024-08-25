package com.prodapt.networkticketingapplicationproject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.GetCustomerTickets;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketRequest;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateRequest;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTicketSuccess() {
        TicketRequest requestTicket = new TicketRequest("title", "description", "username");
        Ticket ticket = new Ticket();
        ticket.setTitle(requestTicket.getTitle());
        ticket.setDescription(requestTicket.getDescription());
        
        UserEntity userEntity = new UserEntity();
        when(userEntityService.findByUsername(requestTicket.getUsername())).thenReturn(Optional.of(userEntity));
        when(ticketService.addTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = customerController.addTicket(requestTicket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testAddTicketFailure() {
        TicketRequest requestTicket = new TicketRequest("title", "description", "username");
        
        when(userEntityService.findByUsername(requestTicket.getUsername())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            customerController.addTicket(requestTicket);
        });
    }

    @Test
    void testUpdateTicketSuccess() throws TicketNotFoundException {
        TicketUpdateRequest requestTicket = new TicketUpdateRequest(1, "newTitle", "newDescription");
        Ticket ticket = new Ticket();
        ticket.setTitle(requestTicket.getTitle());
        ticket.setDescription(requestTicket.getDescription());
        
        when(ticketService.getTicketById(requestTicket.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = customerController.updateTicket(requestTicket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testUpdateTicketFailure() throws TicketNotFoundException {
        TicketUpdateRequest requestTicket = new TicketUpdateRequest(1, "newTitle", "newDescription");
        
        when(ticketService.getTicketById(requestTicket.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            customerController.updateTicket(requestTicket);
        });
    }

    
    
    @Test
    void testGetTicketSuccess() throws TicketNotFoundException {
        GetByTicketId getByTicketId = new GetByTicketId(1);
        Ticket ticket = new Ticket();
        
        when(ticketService.getTicketById(getByTicketId.getTicketId())).thenReturn(ticket);

        ResponseEntity<Ticket> response = customerController.getTicket(getByTicketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testGetTicketFailure() throws TicketNotFoundException {
        GetByTicketId getByTicketId = new GetByTicketId(1);
        
        when(ticketService.getTicketById(getByTicketId.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            customerController.getTicket(getByTicketId);
        });
    }

    @Test
    void testGetMyTicketsSuccess() throws TicketNotFoundException {
        GetCustomerTickets getCustomerTickets = new GetCustomerTickets("username");
        UserEntity userEntity = new UserEntity();
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        
        when(userEntityService.findByUsername(getCustomerTickets.getUsername())).thenReturn(Optional.of(userEntity));
        when(ticketService.getByUserEntity(userEntity)).thenReturn(tickets);

        ResponseEntity<List<Ticket>> response = customerController.getMyTicket(getCustomerTickets);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testGetMyTicketsFailure() throws TicketNotFoundException {
        GetCustomerTickets getCustomerTickets = new GetCustomerTickets("username");

        when(userEntityService.findByUsername(getCustomerTickets.getUsername())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            customerController.getMyTicket(getCustomerTickets);
        });

        assertEquals("No value present", exception.getMessage());
    }

    
    @Test
    void testGetMyTicketsException() throws TicketNotFoundException {
        GetCustomerTickets getCustomerTickets = new GetCustomerTickets("username");
        UserEntity userEntity = new UserEntity();
        
        when(userEntityService.findByUsername(getCustomerTickets.getUsername())).thenReturn(Optional.of(userEntity));
        when(ticketService.getByUserEntity(userEntity)).thenThrow(new TicketNotFoundException("Tickets not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            customerController.getMyTicket(getCustomerTickets);
        });
    }


}
