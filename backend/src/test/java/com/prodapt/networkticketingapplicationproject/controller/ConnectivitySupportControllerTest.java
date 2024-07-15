package com.prodapt.networkticketingapplicationproject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateIT;
import com.prodapt.networkticketingapplicationproject.service.TicketService;

public class ConnectivitySupportControllerTest {

    @InjectMocks
    private ConnectivitySupportController connectivitySupportController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateTicketSuccess() throws TicketNotFoundException {
        TicketUpdateIT request = new TicketUpdateIT(1, Status.CLOSE);
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setStatus(Status.OPEN);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = connectivitySupportController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testUpdateTicketFailure() throws TicketNotFoundException {
        TicketUpdateIT request = new TicketUpdateIT(1, Status.CLOSE);

        when(ticketService.getTicketById(request.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.updateTicket(request);
        });
    }

    @Test
    void testGetTicketSuccess() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId(1);
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);

        ResponseEntity<Ticket> response = connectivitySupportController.getTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testGetTicketFailure() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId(1);

        when(ticketService.getTicketById(request.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.getTicket(request);
        });
    }

    @Test
    void testGetConnectivityLowPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> lowPriorityTickets = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> connectivityTickets = Arrays.asList(lowPriorityTickets.get(0));

        when(ticketService.findTicketsWithLowPriorityAndVariousSeverities()).thenReturn(lowPriorityTickets);
        when(ticketService.connectivityIssue()).thenReturn(connectivityTickets);

        ResponseEntity<List<Ticket>> response = connectivitySupportController.getConnectivityLowPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(lowPriorityTickets.get(0), response.getBody().get(0));
    }

    @Test
    void testGetConnectivityLowPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithLowPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.connectivityIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.getConnectivityLowPriorityTickets();
        });
    }

    @Test
    void testGetConnectivityMediumPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> mediumPriorityTickets = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> connectivityTickets = Arrays.asList(mediumPriorityTickets.get(0));

        when(ticketService.findTicketsWithMediumPriorityAndVariousSeverities()).thenReturn(mediumPriorityTickets);
        when(ticketService.connectivityIssue()).thenReturn(connectivityTickets);

        ResponseEntity<List<Ticket>> response = connectivitySupportController.getConnectivityMediumPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mediumPriorityTickets.get(0), response.getBody().get(0));
    }

    @Test
    void testGetConnectivityMediumPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithMediumPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.connectivityIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.getConnectivityMediumPriorityTickets();
        });
    }

    @Test
    void testGetConnectivityHighPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> highPriorityTickets = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> connectivityTickets = Arrays.asList(highPriorityTickets.get(0));

        when(ticketService.findTicketsWithHighPriorityAndVariousSeverities()).thenReturn(highPriorityTickets);
        when(ticketService.connectivityIssue()).thenReturn(connectivityTickets);

        ResponseEntity<List<Ticket>> response = connectivitySupportController.getConnectivityHighPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(highPriorityTickets.get(0), response.getBody().get(0));
    }

    @Test
    void testGetConnectivityHighPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithHighPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.connectivityIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.getConnectivityHighPriorityTickets();
        });
    }

    @Test
    void testGetCriticalTicket_Success() throws TicketNotFoundException {
        // Create test data
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
 
        List<Ticket> connectivityissue = Arrays.asList(ticket1, ticket2, ticket3);
        List<Ticket> agedTickets = Arrays.asList(ticket1, ticket2);
        List<Ticket> openTickets = Arrays.asList(ticket1, ticket2);
 
        // Mock the service method calls
        when(ticketService.connectivityIssue()).thenReturn(connectivityissue);
        when(ticketService.getFourtyEightPlusAgedTickets()).thenReturn(agedTickets);
        when(ticketService.getOpenTickets()).thenReturn(openTickets);
 
        // Call the controller method
        ResponseEntity<List<Ticket>> response = connectivitySupportController.getCriticalTicket();
 
        // Verify the result
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains(ticket1));
        assertTrue(response.getBody().contains(ticket2));
 
        // Verify service method calls
        verify(ticketService, times(1)).connectivityIssue();
        verify(ticketService, times(1)).getFourtyEightPlusAgedTickets();
        verify(ticketService, times(1)).getOpenTickets();
    }

    @Test
    void testGetCriticalTicketFailure() throws TicketNotFoundException {
        when(ticketService.getFourtyEightPlusAgedTickets()).thenReturn(Collections.emptyList());
        when(ticketService.connectivityIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            connectivitySupportController.getCriticalTicket();
        });
    }
}
