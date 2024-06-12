package com.prodapt.networkticketingapplicationproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.repositories.TicketRepository;

public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;
    private UserEntity userEntity;
    private List<Ticket> ticketList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket();
        userEntity = new UserEntity();
        ticketList = new ArrayList<>();
    }

    @Test
    public void testAddTicket() {
        ticket.setTicketId(1);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket savedTicket = ticketService.addTicket(ticket);

        assertNotNull(savedTicket);
        assertEquals(Status.OPEN, savedTicket.getStatus());
        assertEquals(LocalDate.now(), savedTicket.getCreationDate());
        assertEquals(LocalDate.now(), savedTicket.getLastUpdated());
    }

    @Test
    public void testGetTicketById() throws Exception {
        ticket.setTicketId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        Ticket retrievedTicket = ticketService.getTicketById(1);

        assertNotNull(retrievedTicket);
        assertEquals(ticket, retrievedTicket);
    }

    @Test
    public void testUpdateTicket() throws Exception {
        ticket.setTicketId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket updatedTicket = ticketService.updateTicket(ticket);

        assertNotNull(updatedTicket);
        assertEquals(ticket, updatedTicket);
    }

    @Test
    public void testGetByPriority() throws Exception {
        ticket.setPriority(Priority.HIGH);
        ticketList.add(ticket);
        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getByPriority(Priority.HIGH);

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

    @Test
    public void testGetBySeverity() throws Exception {
        ticket.setSeverity(Severity.MEDIUM);
        ticketList.add(ticket);
        when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getBySeverity(Severity.MEDIUM);

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

    @Test
    public void testGetByUserEntity() throws Exception {
        ticket.setUser(userEntity);
        ticketList.add(ticket);
        when(ticketRepository.findByUser(userEntity)).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getByUserEntity(userEntity);

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

    @Test
    public void testGetAllTickets() throws Exception {
        ticketList.add(ticket);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getAllTickets();

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

//    @Test
//    public void testGetMinorTickets() throws Exception, TicketNotFoundException {
//        Ticket lowPriorityLowSeverityTicket = new Ticket();
//        lowPriorityLowSeverityTicket.setPriority(Priority.LOW);
//        lowPriorityLowSeverityTicket.setSeverity(Severity.LOW);
//        lowPriorityLowSeverityTicket.setStatus(Status.OPEN);
//        lowPriorityLowSeverityTicket.setCreationDate(LocalDate.now()); // Set a valid creationDate
//
//        ticketList.add(lowPriorityLowSeverityTicket);
//        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(Arrays.asList(lowPriorityLowSeverityTicket));
//        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(Arrays.asList(lowPriorityLowSeverityTicket));
//        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(ticketList);
//        when(ticketRepository.findAll()).thenReturn(ticketList); // Mock getAllTickets() to return ticketList
//
//        List<Ticket> retrievedTickets = ticketService.getMinorTickets();
//
//        assertNotNull(retrievedTickets);
//        assertIterableEquals(ticketList, retrievedTickets);
//    }

    
    @Test
    public void testGetCriticalTickets() throws Exception {
        Ticket highPriorityHighSeverityTicket = new Ticket();
        highPriorityHighSeverityTicket.setPriority(Priority.HIGH);
        highPriorityHighSeverityTicket.setSeverity(Severity.HIGH);
        highPriorityHighSeverityTicket.setStatus(Status.OPEN);
        highPriorityHighSeverityTicket.setCreationDate(LocalDate.now().minusDays(3));

        Ticket goldMediumTicket = new Ticket();
        goldMediumTicket.setCustomerTier(CustomerTier.GOLD);
        goldMediumTicket.setPriority(Priority.MEDIUM);
        goldMediumTicket.setStatus(Status.OPEN);
        goldMediumTicket.setCreationDate(LocalDate.now().minusDays(3));

        ticketList.addAll(Arrays.asList(highPriorityHighSeverityTicket, goldMediumTicket));
        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(Arrays.asList(highPriorityHighSeverityTicket));
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(Arrays.asList(highPriorityHighSeverityTicket));
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(Arrays.asList(goldMediumTicket));
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(Arrays.asList(goldMediumTicket));
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(ticketList);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getCriticalTickets();

        assertNotNull(retrievedTickets);
    }
    
    
//    @Test
//    public void testGetUrgentButNotCriticalTickets() throws Exception {
//        // Create test data
//        Ticket highPriorityMediumSeverityTicket = new Ticket();
//        highPriorityMediumSeverityTicket.setPriority(Priority.HIGH);
//        highPriorityMediumSeverityTicket.setSeverity(Severity.MEDIUM);
//        highPriorityMediumSeverityTicket.setStatus(Status.OPEN);
//
//        Ticket goldMediumTicket = new Ticket();
//        goldMediumTicket.setCustomerTier(CustomerTier.GOLD);
//        goldMediumTicket.setPriority(Priority.MEDIUM);
//        goldMediumTicket.setStatus(Status.OPEN);
//
//        List<Ticket> ticketList = new ArrayList<>();
//        ticketList.add(highPriorityMediumSeverityTicket);
//        ticketList.add(goldMediumTicket);
//
//        // Mock the repository and define behavior
//        when(ticketRepository.findByPriority(Priority.HIGH))
//                .thenReturn(Collections.singletonList(highPriorityMediumSeverityTicket));
//        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD))
//                .thenReturn(Collections.singletonList(goldMediumTicket));
//        when(ticketRepository.findByStatus(Status.OPEN))
//                .thenReturn(ticketList);
//
//        // Call the service method
//        List<Ticket> retrievedTickets = ticketService.getUrgentButNotCriticalTickets();
//
//        // Assertions
//        assertNotNull(retrievedTickets);
//        assertEquals(1, retrievedTickets.size());
//        assertTrue(retrievedTickets.contains(goldMediumTicket));
//        assertFalse(retrievedTickets.contains(highPriorityMediumSeverityTicket));
//    }


    @Test
    public void testGetNotUrgentButCriticalTickets() throws Exception {
        Ticket lowPriorityHighSeverityTicket = new Ticket();
        lowPriorityHighSeverityTicket.setPriority(Priority.LOW);
        lowPriorityHighSeverityTicket.setSeverity(Severity.HIGH);
        lowPriorityHighSeverityTicket.setStatus(Status.OPEN);
        lowPriorityHighSeverityTicket.setCreationDate(LocalDate.now().minusDays(3));

        Ticket twentyFourHoursPlusTicket = new Ticket();
        twentyFourHoursPlusTicket.setCreationDate(LocalDate.now().minusDays(2));
        twentyFourHoursPlusTicket.setStatus(Status.OPEN);

        ticketList.addAll(Arrays.asList(lowPriorityHighSeverityTicket, twentyFourHoursPlusTicket));
        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(Arrays.asList(lowPriorityHighSeverityTicket));
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(Arrays.asList(lowPriorityHighSeverityTicket));
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(ticketList);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getNotUrgentButCriticalTickets();

        assertNotNull(retrievedTickets);
        assertNotEquals(ticketList, retrievedTickets);
    }

    
    @Test
    public void testGetFourtyEightPlusAgedTickets() throws Exception {
        Ticket fourtyEightHoursPlusTicket = new Ticket();
        fourtyEightHoursPlusTicket.setCreationDate(LocalDate.now().minusDays(3));

        ticketList.add(fourtyEightHoursPlusTicket);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getFourtyEightPlusAgedTickets();

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

    @Test
    public void testGetTwentyFourPlusAgedTickets() throws Exception {
        Ticket twentyFourHoursPlusTicket = new Ticket();
        twentyFourHoursPlusTicket.setCreationDate(LocalDate.now().minusDays(2));

        ticketList.add(twentyFourHoursPlusTicket);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getTwentyFourPlusAgedTickets();

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }

    @Test
    public void testGetOpenTickets() throws Exception {
        Ticket openTicket = new Ticket();
        openTicket.setStatus(Status.OPEN);

        ticketList.add(openTicket);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(ticketList);

        List<Ticket> retrievedTickets = ticketService.getOpenTickets();

        assertNotNull(retrievedTickets);
        assertEquals(ticketList, retrievedTickets);
    }
    
    @Test
    public void testAddTicket_NullTicket() {
        assertThrows(NullPointerException.class, () -> ticketService.addTicket(null));
    }

    @Test
    public void testGetTicketById_NonExistentTicket() {
        when(ticketRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(1));
    }

    @Test
    public void testUpdateTicket_NonExistentTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.updateTicket(ticket));
    }

    @Test
    public void testGetByPriority_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getByPriority(Priority.HIGH));
    }

    @Test
    public void testGetBySeverity_NoTicketsFound() {
        when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getBySeverity(Severity.MEDIUM));
    }

    @Test
    public void testGetByUserEntity_NoTicketsFound() {
        UserEntity userEntity = new UserEntity();
        when(ticketRepository.findByUser(userEntity)).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getByUserEntity(userEntity));
    }

    @Test
    public void testGetAllTickets_NoTicketsFound() {
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getAllTickets());
    }

    @Test
    public void testGetRoutineTickets_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getRoutineTickets());
    }
    @Test
    public void testGetMinorTickets_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(new ArrayList<>());
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getMinorTickets());
    }

    @Test
    public void testGetCriticalTickets_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(new ArrayList<>());
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getCriticalTickets());
    }

    @Test
    public void testGetUrgentButNotCriticalTickets_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(new ArrayList<>());
        when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getUrgentButNotCriticalTickets());
    }

    @Test
    public void testGetNotUrgentButCriticalTickets_NoTicketsFound() {
        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(new ArrayList<>());
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(new ArrayList<>());
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getNotUrgentButCriticalTickets());
    }

    @Test
    public void testGetFourtyEightPlusAgedTickets_NoTicketsFound() {
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getFourtyEightPlusAgedTickets());
    }

    @Test
    public void testGetTwentyFourPlusAgedTickets_NoTicketsFound() {
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTwentyFourPlusAgedTickets());
    }

    @Test
    public void testGetOpenTickets_NoTicketsFound() {
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getOpenTickets());
    }
}