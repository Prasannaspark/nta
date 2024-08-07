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

public class PerformanceSupportControllerTest {

    @InjectMocks
    private PerformanceSupportController performanceSupportController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateTicketSuccess() throws TicketNotFoundException {
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(1);
        request.setStatus(Status.CLOSE);

        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setStatus(Status.OPEN);

        when(ticketService.getTicketById(1)).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = performanceSupportController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testUpdateTicketFailure() throws TicketNotFoundException {
        TicketUpdateIT request = new TicketUpdateIT();
        request.setTicketId(1);
        request.setStatus(Status.CLOSE);

        when(ticketService.getTicketById(1)).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.updateTicket(request);
        });
    }

    @Test
    void testGetTicketSuccess() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(1);

        Ticket ticket = new Ticket();
        ticket.setTicketId(1);

        when(ticketService.getTicketById(1)).thenReturn(ticket);

        ResponseEntity<Ticket> response = performanceSupportController.getTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testGetTicketFailure() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId();
        request.setTicketId(1);

        when(ticketService.getTicketById(1)).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.getTicket(request);
        });
    }

    @Test
    void testGetPerformanceLowPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> mpvs = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> performanceissue = Arrays.asList(mpvs.get(0));

        when(ticketService.findTicketsWithLowPriorityAndVariousSeverities()).thenReturn(mpvs);
        when(ticketService.performanceIssue()).thenReturn(performanceissue);

        ResponseEntity<List<Ticket>> response = performanceSupportController.getPerformanceLowPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mpvs.get(0), response.getBody().get(0));
    }

    @Test
    void testGetPerformanceLowPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithLowPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.performanceIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.getPerformanceLowPriorityTickets();
        });
    }

    @Test
    void testGetPerformanceMediumPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> mpvs = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> performanceissue = Arrays.asList(mpvs.get(0));

        when(ticketService.findTicketsWithMediumPriorityAndVariousSeverities()).thenReturn(mpvs);
        when(ticketService.performanceIssue()).thenReturn(performanceissue);

        ResponseEntity<List<Ticket>> response = performanceSupportController.getPerformanceMediumPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mpvs.get(0), response.getBody().get(0));
    }

    @Test
    void testGetPerformanceMediumPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithMediumPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.performanceIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.getPerformanceMediumPriorityTickets();
        });
    }

    @Test
    void testGetPerformanceHighPriorityTicketsSuccess() throws TicketNotFoundException {
        List<Ticket> mpvs = Arrays.asList(new Ticket(), new Ticket());
        List<Ticket> performanceissue = Arrays.asList(mpvs.get(0));

        when(ticketService.findTicketsWithHighPriorityAndVariousSeverities()).thenReturn(mpvs);
        when(ticketService.performanceIssue()).thenReturn(performanceissue);

        ResponseEntity<List<Ticket>> response = performanceSupportController.getPerformanceHighPriorityTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mpvs.get(0), response.getBody().get(0));
    }

    @Test
    void testGetPerformanceHighPriorityTicketsFailure() throws TicketNotFoundException {
        when(ticketService.findTicketsWithHighPriorityAndVariousSeverities()).thenReturn(Collections.emptyList());
        when(ticketService.performanceIssue()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.getPerformanceHighPriorityTickets();
        });
    }

    @Test
    void testGetCriticalTicket_Success() throws TicketNotFoundException {
        // Create test data
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
 
        List<Ticket> performanceissue = Arrays.asList(ticket1, ticket2, ticket3);
        List<Ticket> agedTickets = Arrays.asList(ticket1, ticket2);
        List<Ticket> openTickets = Arrays.asList(ticket1, ticket2);
 
        // Mock the service method calls
        when(ticketService.performanceIssue()).thenReturn(performanceissue);
        when(ticketService.getFourtyEightPlusAgedTickets()).thenReturn(agedTickets);
        when(ticketService.getOpenTickets()).thenReturn(openTickets);
 
        // Call the controller method
        ResponseEntity<List<Ticket>> response = performanceSupportController.getCriticalTicket();
 
        // Verify the result
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains(ticket1));
        assertTrue(response.getBody().contains(ticket2));
 
        // Verify service method calls
        verify(ticketService, times(1)).performanceIssue();
        verify(ticketService, times(1)).getFourtyEightPlusAgedTickets();
        verify(ticketService, times(1)).getOpenTickets();
    }

    @Test
    void testGetCriticalTicketFailure() throws TicketNotFoundException {
        when(ticketService.performanceIssue()).thenReturn(Collections.emptyList());
        when(ticketService.getFourtyEightPlusAgedTickets()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            performanceSupportController.getCriticalTicket();
        });
    }
}
