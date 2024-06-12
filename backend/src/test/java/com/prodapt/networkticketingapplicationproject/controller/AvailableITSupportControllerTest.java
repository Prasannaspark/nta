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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabeITSupportControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private AvailabeITSupportController availabeITSupportController;

    private Ticket dummyTicket;

    @BeforeEach
    void setUp() {
        dummyTicket = new Ticket();
        dummyTicket.setTicketId(1);
        dummyTicket.setStatus(Status.OPEN);
    }

    @Test
    void testGetRoutineTicket_Success() throws TicketNotFoundException {
        // Arrange
        List<Ticket> routineTickets = new ArrayList<>();
        routineTickets.add(dummyTicket);
        when(ticketService.getRoutineTickets()).thenReturn(routineTickets);

        // Act
        ResponseEntity<List<Ticket>> response = availabeITSupportController.getRoutineTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(routineTickets, response.getBody());
    }

    @Test
    void testGetMinorTicket_Success() throws TicketNotFoundException {
        // Arrange
        List<Ticket> minorTickets = new ArrayList<>();
        minorTickets.add(dummyTicket);
        when(ticketService.getMinorTickets()).thenReturn(minorTickets);

        // Act
        ResponseEntity<List<Ticket>> response = availabeITSupportController.getMinorTicket();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(minorTickets, response.getBody());
    }

    @Test
    void testUpdateTicket_Success() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());
        request.setStatus(Status.CLOSE);
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenReturn(dummyTicket);

        // Act
        ResponseEntity<Ticket> response = availabeITSupportController.updateTicket(request);

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
        ResponseEntity<Ticket> response = availabeITSupportController.getTicket(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dummyTicket, response.getBody());
    }

    @Test
    void testGetRoutineTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getRoutineTickets()).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> availabeITSupportController.getRoutineTicket());
    }

    @Test
    void testGetMinorTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        when(ticketService.getMinorTickets()).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> availabeITSupportController.getMinorTicket());
    }

    @Test
    void testUpdateTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(dummyTicket.getTicketId());
        request.setStatus(Status.CLOSE);
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> availabeITSupportController.updateTicket(request));
    }

    @Test
    void testGetTicket_TicketNotFoundException() throws TicketNotFoundException {
        // Arrange
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(dummyTicket.getTicketId());
        when(ticketService.getTicketById(dummyTicket.getTicketId())).thenThrow(TicketNotFoundException.class);

        // Act & Assert
        assertThrows(TicketNotFoundException.class, () -> availabeITSupportController.getTicket(request));
    }
}
