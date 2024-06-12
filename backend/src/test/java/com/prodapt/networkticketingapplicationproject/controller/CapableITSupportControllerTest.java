package com.prodapt.networkticketingapplicationproject.controller;

import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateIT;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CapableITSupportControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private CapableITSupportController capableITSupportController;

    private Ticket dummyTicket;

    @BeforeEach
    void setUp() {
        dummyTicket = new Ticket();
        dummyTicket.setTicketId(1);
        dummyTicket.setStatus(Status.OPEN);
    }

    @Test
    void testGetUrgentButNotCriticalTicket_Success() throws TicketNotFoundException {
        // Arrange
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(dummyTicket);
        when(ticketService.getUrgentButNotCriticalTickets()).thenReturn(tickets);

        // Act
        ResponseEntity<List<Ticket>> response = capableITSupportController.getUrgentButNotCriticalTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testGetNotUrgentButCriticalTicket_Success() throws TicketNotFoundException {
        // Arrange
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(dummyTicket);
        when(ticketService.getNotUrgentButCriticalTickets()).thenReturn(tickets);

        // Act
        ResponseEntity<List<Ticket>> response = capableITSupportController.getNotUrgentButCriticalTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testUpdateTicket_Success() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());
        request.setStatus(Status.CLOSE);
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = capableITSupportController.updateTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetTicket_Success() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = capableITSupportController.getTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dummyTicket, response.getBody());
    }

    @Test
    void testGetUrgentButNotCriticalTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getUrgentButNotCriticalTickets()).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> capableITSupportController.getUrgentButNotCriticalTicket());
    }

    @Test
    void testGetNotUrgentButCriticalTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getNotUrgentButCriticalTickets()).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> capableITSupportController.getNotUrgentButCriticalTicket());
    }

    @Test
    void testUpdateTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());
        request.setStatus(Status.CLOSE);
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> capableITSupportController.updateTicket(request));
    }

    @Test
    void testGetTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> capableITSupportController.getTicket(request));
    }
}
