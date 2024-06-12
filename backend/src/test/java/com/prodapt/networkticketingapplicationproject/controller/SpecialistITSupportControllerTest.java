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
class SpecialistITSupportControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private SpecialistITSupportController specialistITSupportController;

    private Ticket dummyTicket;

    @BeforeEach
    void setUp() {
        dummyTicket = new Ticket();
        dummyTicket.setTicketId(1);;
    }

    @Test
    void testGetCriticalTicket_Success() throws TicketNotFoundException {
        // Arrange
        List<Ticket> criticalTickets = new ArrayList<>();
        criticalTickets.add(dummyTicket);

        when(ticketService.getCriticalTickets()).thenReturn(criticalTickets);

        // Act
        ResponseEntity<List<Ticket>> response = specialistITSupportController.getCriticalTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(criticalTickets, response.getBody());
    }

    @Test
    void testGetCriticalTicket_NoTicketsFound() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getCriticalTickets()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Ticket>> response = specialistITSupportController.getCriticalTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testUpdateTicket_Success() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());
        request.setStatus(Status.OPEN);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setTicketId(dummyTicket.getTicketId());
        updatedTicket.setStatus(Status.OPEN);
        updatedTicket.setLastUpdated(LocalDate.now());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);
        when(ticketService.updateTicket(dummyTicket)).thenReturn(updatedTicket);

        // Act
        ResponseEntity<Ticket> response = specialistITSupportController.updateTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedTicket, response.getBody());
    }

    @Test
    void testUpdateTicket_TicketNotFound() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> specialistITSupportController.updateTicket(request));
    }

    @Test
    void testGetTicket_Success() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());

        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = specialistITSupportController.getTicket(request);

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
        assertThrows(TicketNotFoundException.class, () -> specialistITSupportController.getTicket(request));
    }
}
