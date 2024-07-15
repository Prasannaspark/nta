package com.prodapt.networkticketingapplicationproject.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.networkticketingapplicationproject.controller.AdminController;
import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.IssueType;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptionmessages.QueryMapper;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.model.PriorityAnalysisReport;
import com.prodapt.networkticketingapplicationproject.model.ResolutionTimeReport;
import com.prodapt.networkticketingapplicationproject.model.TicketAgingReport;
import com.prodapt.networkticketingapplicationproject.repositories.TicketRepository;
import com.prodapt.networkticketingapplicationproject.repositories.UserRepository;
 
public class TicketServiceImplTest {
 
    @Mock
    private TicketRepository repo;
    
    @Mock
    private UserRepository userrepo;
 
    @InjectMocks
    private TicketServiceImpl service;
    
    @InjectMocks
    private AdminController adminController;
    
   
 
    private Ticket ticket;
    private UserEntity user;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1);
        user.setUsername("testuser");
 
        ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Test Description");
        ticket.setPriority(Priority.HIGH);
        ticket.setSeverity(Severity.HIGH);
        ticket.setStatus(Status.OPEN);
        ticket.setCreationDate(LocalDate.now());
        ticket.setLastUpdated(LocalDate.now());
        ticket.setCustomerTier(CustomerTier.GOLD);
        ticket.setUser(user);
    }
 
    
    // Success Test Cases
    
    @Test
    void testGetOpenTickets_Success() throws TicketNotFoundException {
        List<Ticket> openTickets = new ArrayList<>();
        openTickets.add(new Ticket());
        openTickets.add(new Ticket());

        when(repo.findByStatus(Status.OPEN)).thenReturn(openTickets);

        List<Ticket> result = service.getOpenTickets();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testAddTicket_Success() {
        Ticket ticket = new Ticket();
        ticket.setTitle("Sample Ticket");
        ticket.setPriority(Priority.HIGH);
        ticket.setSeverity(Severity.HIGH);
        UserEntity user = new UserEntity();
        user.setId(1); 
        user.setTier(CustomerTier.SILVER); 
        ticket.setUser(user);

        // Initialize the userrepo field
        when(userrepo.findById(anyInt())).thenReturn(Optional.of(user));

        when(repo.save(any(Ticket.class))).thenReturn(ticket);

        Ticket addedTicket = service.addTicket(ticket);

        assertNotNull(addedTicket);
        assertEquals(Status.OPEN, addedTicket.getStatus());
        assertNotNull(addedTicket.getCreationDate());
        assertNotNull(addedTicket.getLastUpdated());
        assertEquals(CustomerTier.SILVER, addedTicket.getCustomerTier());
        verify(repo, times(1)).save(any(Ticket.class));
        verify(userrepo, times(1)).findById(anyInt());
    }

    @Test
    void testGetTicketById_Success() throws TicketNotFoundException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTitle("Sample Ticket");

        when(repo.findById(1)).thenReturn(Optional.of(ticket));

        Ticket retrievedTicket = service.getTicketById(1);

        assertNotNull(retrievedTicket);
        assertEquals(ticket.getTitle(), retrievedTicket.getTitle());
        verify(repo, times(1)).findById(1);
    }

    @Test
    void testUpdateTicket_Success() throws TicketNotFoundException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTitle("Sample Ticket");

        when(repo.findById(1)).thenReturn(Optional.of(ticket));
        when(repo.save(any(Ticket.class))).thenReturn(ticket);

        Ticket updatedTicket = service.updateTicket(ticket);

        assertNotNull(updatedTicket);
        assertEquals(ticket.getTitle(), updatedTicket.getTitle());
        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(any(Ticket.class));
    }

    @Test
    void testGetByPriority_Success() throws TicketNotFoundException {
        Priority priority = Priority.HIGH;
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByPriority(priority)).thenReturn(tickets);

        List<Ticket> result = service.getByPriority(priority);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByPriority(priority);
    }

    @Test
    void testGetBySeverity_Success() throws TicketNotFoundException {
        Severity severity = Severity.HIGH;
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findBySeverity(severity)).thenReturn(tickets);

        List<Ticket> result = service.getBySeverity(severity);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findBySeverity(severity);
    }

    @Test
    void testGetByUserEntity_Success() throws TicketNotFoundException {
        UserEntity user = new UserEntity();
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByUser(user)).thenReturn(tickets);

        List<Ticket> result = service.getByUserEntity(user);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByUser(user);
    }

    @Test
    void testGetAllTickets_Success() throws TicketNotFoundException {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findAll()).thenReturn(tickets);

        List<Ticket> result = service.getAllTickets();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetFourtyEightPlusAgedTickets_Success() throws TicketNotFoundException {
        LocalDate fortyEightHoursAgo = LocalDate.now().minusDays(3);
        Ticket ticket1 = new Ticket();
        ticket1.setCreationDate(fortyEightHoursAgo);
        Ticket ticket2 = new Ticket();
        ticket2.setCreationDate(fortyEightHoursAgo);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(repo.findAll()).thenReturn(tickets);

        List<Ticket> result = service.getFourtyEightPlusAgedTickets();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }
    
    @Test
    void testGetFourtyEightPlusAgedTickets_NoTicketsFound1() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(TicketNotFoundException.class, () -> service.getFourtyEightPlusAgedTickets());
    }

    @Test
    void testGetTwentyFourPlusAgedTickets_Success() throws TicketNotFoundException {
        LocalDate twentyFourHoursAgo = LocalDate.now().minusDays(3);
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId(1);
        ticket1.setTitle("Ticket 1");
        ticket1.setPriority(Priority.HIGH);
        ticket1.setSeverity(Severity.HIGH);
        ticket1.setStatus(Status.OPEN);
        ticket1.setCreationDate(twentyFourHoursAgo);
        ticket1.setLastUpdated(LocalDate.now());
        ticket1.setUser(null);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId(2);
        ticket2.setTitle("Ticket 2");
        ticket2.setPriority(Priority.HIGH);
        ticket2.setSeverity(Severity.HIGH);
        ticket2.setStatus(Status.OPEN);
        ticket2.setCreationDate(twentyFourHoursAgo);
        ticket2.setLastUpdated(LocalDate.now());
        ticket2.setUser(null);

        List<Ticket> tickets = List.of(ticket1, ticket2);

        when(repo.findAll()).thenReturn(tickets);

        List<Ticket> result = service.getTwentyFourPlusAgedTickets();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testHardwareIssue_Success() throws TicketNotFoundException {
        List<Ticket> hardwareTickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByIssueType(IssueType.HARDWARE)).thenReturn(hardwareTickets);

        List<Ticket> result = service.hardwareIssue();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByIssueType(IssueType.HARDWARE);
    }

    @Test
    void testConnectivityIssue_Success() throws TicketNotFoundException {
        List<Ticket> connectivityTickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByIssueType(IssueType.CONNECTIVITY)).thenReturn(connectivityTickets);

        List<Ticket> result = service.connectivityIssue();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByIssueType(IssueType.CONNECTIVITY);
    }

    @Test
    void testPerformanceIssue_Success() throws TicketNotFoundException {
        List<Ticket> performanceTickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByIssueType(IssueType.PERFORMANCE)).thenReturn(performanceTickets);

        List<Ticket> result = service.performanceIssue();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByIssueType(IssueType.PERFORMANCE);
    }

    @Test
    void testAdministrativeIssue_Success() throws TicketNotFoundException {
        List<Ticket> adminTickets = Arrays.asList(new Ticket(), new Ticket());

        when(repo.findByIssueType(IssueType.ADMINISTRATIVE)).thenReturn(adminTickets);

        List<Ticket> result = service.administrativeIssue();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByIssueType(IssueType.ADMINISTRATIVE);
    }

    @Test
    void testGoldTicketHandler_Success() throws TicketNotFoundException {
        Ticket goldTicket1 = new Ticket();
        goldTicket1.setCustomerTier(CustomerTier.GOLD);
        goldTicket1.setStatus(Status.OPEN);

        Ticket goldTicket2 = new Ticket();
        goldTicket2.setCustomerTier(CustomerTier.GOLD);
        goldTicket2.setStatus(Status.OPEN);

        List<Ticket> goldTickets = Arrays.asList(goldTicket1, goldTicket2);
        List<Ticket> openTickets = Arrays.asList(goldTicket1, goldTicket2);

        when(repo.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTickets);
        when(repo.findByStatus(Status.OPEN)).thenReturn(openTickets);

        List<Ticket> result = service.goldTicketHandler();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByCustomerTier(CustomerTier.GOLD);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }
    
    @Test
    void testSilverTicketHandler_Success() throws TicketNotFoundException {
        Ticket silverTicket1 = new Ticket();
        silverTicket1.setCustomerTier(CustomerTier.SILVER);
        silverTicket1.setStatus(Status.OPEN);

        Ticket silverTicket2 = new Ticket();
        silverTicket2.setCustomerTier(CustomerTier.SILVER);
        silverTicket2.setStatus(Status.OPEN);

        List<Ticket> silverTickets = Arrays.asList(silverTicket1, silverTicket2);
        List<Ticket> openTickets = Arrays.asList(silverTicket1, silverTicket2);

        when(repo.findByCustomerTier(CustomerTier.SILVER)).thenReturn(silverTickets);
        when(repo.findByStatus(Status.OPEN)).thenReturn(openTickets);

        List<Ticket> result = service.silverTicketHandler();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo, times(1)).findByCustomerTier(CustomerTier.SILVER);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }
    
    @Test
    void testFindTicketsWithHighPriorityAndVariousSeverities_ThrowsTicketNotFoundException() {
        when(repo.findByPriority(Priority.HIGH)).thenReturn(new ArrayList<>());
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(new ArrayList<>());
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(new ArrayList<>());
        when(repo.findBySeverity(Severity.LOW)).thenReturn(new ArrayList<>());
        when(repo.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(TicketNotFoundException.class, () -> {
            service.findTicketsWithHighPriorityAndVariousSeverities();
        });
    }
    
    @Test
    void testFindTicketsWithMediumPriorityAndVariousSeverities_ThrowsException() throws TicketNotFoundException {
        List<Ticket> mediumPriorityTickets = Collections.emptyList();
        List<Ticket> openTickets = Collections.emptyList();

        // Mock repository method calls
        when(repo.findByPriority(Priority.MEDIUM)).thenReturn(mediumPriorityTickets);
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(Collections.emptyList()); // No tickets with HIGH severity
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(Collections.emptyList()); // No tickets with MEDIUM severity
        when(repo.findBySeverity(Severity.LOW)).thenReturn(Collections.emptyList()); // No tickets with LOW severity
        when(repo.findByStatus(Status.OPEN)).thenReturn(openTickets);

        // Call the service method
        try {
            service.findTicketsWithMediumPriorityAndVariousSeverities();
            fail("Expected TicketNotFoundException to be thrown");
        } catch (TicketNotFoundException e) {
            assertEquals(QueryMapper.TICKETNOTFOUND, e.getMessage());
        }
    }

    @Test
    void testFindTicketsWithLowPriorityAndVariousSeverities_Success() throws TicketNotFoundException {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId(1);
        ticket1.setTitle("Ticket 1");
        ticket1.setPriority(Priority.LOW);
        ticket1.setSeverity(Severity.HIGH);
        ticket1.setStatus(Status.OPEN);
        ticket1.setCreationDate(LocalDate.now());
        ticket1.setLastUpdated(LocalDate.now());
        ticket1.setUser(null);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId(2);
        ticket2.setTitle("Ticket 2");
        ticket2.setPriority(Priority.LOW);
        ticket2.setSeverity(Severity.MEDIUM);
        ticket2.setStatus(Status.OPEN);
        ticket2.setCreationDate(LocalDate.now());
        ticket2.setLastUpdated(LocalDate.now());
        ticket2.setUser(null);

        Ticket ticket3 = new Ticket();
        ticket3.setTicketId(3);
        ticket3.setTitle("Ticket 3");
        ticket3.setPriority(Priority.LOW);
        ticket3.setSeverity(Severity.LOW);
        ticket3.setStatus(Status.OPEN);
        ticket3.setCreationDate(LocalDate.now());
        ticket3.setLastUpdated(LocalDate.now());
        ticket3.setUser(null);

        List<Ticket> lowPriorityTickets = List.of(ticket1, ticket2, ticket3);

        when(repo.findByPriority(Priority.LOW)).thenReturn(lowPriorityTickets);
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(new ArrayList<>());
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(new ArrayList<>());
        when(repo.findBySeverity(Severity.LOW)).thenReturn(lowPriorityTickets);
        when(repo.findByStatus(Status.OPEN)).thenReturn(lowPriorityTickets);

        List<Ticket> result = service.findTicketsWithLowPriorityAndVariousSeverities();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(repo, times(1)).findByPriority(Priority.LOW);
        verify(repo, times(1)).findBySeverity(Severity.HIGH);
        verify(repo, times(1)).findBySeverity(Severity.MEDIUM);
        verify(repo, times(1)).findBySeverity(Severity.LOW);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testGetPriorityAnalysisReport_Success() throws TicketNotFoundException {
        Object[] reportData1 = {"HIGH", 5L};
        Object[] reportData2 = {"MEDIUM", 3L};
        List<Object[]> results = Arrays.asList(reportData1, reportData2);

        when(repo.generatePriorityAnalysisReport()).thenReturn(results);

        List<PriorityAnalysisReport> reports = service.getPriorityAnalysisReport();

        assertNotNull(reports);
        assertEquals(2, reports.size());
        assertEquals("HIGH", reports.get(0).getPriority());
        assertEquals(5L, reports.get(0).getCount());
        assertEquals("MEDIUM", reports.get(1).getPriority());
        assertEquals(3L, reports.get(1).getCount());
        verify(repo, times(1)).generatePriorityAnalysisReport();
    }

    @Test
    void testGetTicketAgingReport_Success() throws TicketNotFoundException {
        Object[] reportData1 = {1, "User1", "Title1", Date.valueOf("2022-01-01"), "OPEN", "HIGH", Date.valueOf("2022-01-01"), 5L};
        Object[] reportData2 = {2, "User2", "Title2", Date.valueOf("2022-01-02"), "OPEN", "MEDIUM", Date.valueOf("2022-01-02"), 3L};
        List<Object[]> results = Arrays.asList(reportData1, reportData2);

        when(repo.generateTicketAgingReport()).thenReturn(results);

        List<TicketAgingReport> reports = service.getTicketAgingReport();

        assertNotNull(reports);
        assertEquals(2, reports.size());
        assertEquals(1, reports.get(0).getTicket_id());
        assertEquals("User1", reports.get(0).getUser_name());
        assertEquals("Title1", reports.get(0).getTitle());
        assertEquals(LocalDate.of(2022, 1, 1), reports.get(0).getCreation_date());
        assertEquals(Status.OPEN, reports.get(0).getStatus());
        assertEquals(Priority.HIGH, reports.get(0).getPriority());
        assertEquals(LocalDate.of(2022, 1, 1), reports.get(0).getLast_updated());
        assertEquals(5L, reports.get(0).getAging_days());
        assertEquals(2, reports.get(1).getTicket_id());
        assertEquals("User2", reports.get(1).getUser_name());
        assertEquals("Title2", reports.get(1).getTitle());
        assertEquals(LocalDate.of(2022, 1, 2), reports.get(1).getCreation_date());
        assertEquals(Status.OPEN, reports.get(1).getStatus());
        assertEquals(Priority.MEDIUM, reports.get(1).getPriority());
        assertEquals(LocalDate.of(2022, 1, 2), reports.get(1).getLast_updated());
        assertEquals(3L, reports.get(1).getAging_days()); 
        verify(repo, times(1)).generateTicketAgingReport();
    }


    

    // Failure Test Cases
    
    @Test
    void testGetOpenTickets_Failure() {
        when(repo.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());

        assertThrows(TicketNotFoundException.class, () -> service.getOpenTickets());
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }
    
    @Test
    void testAddTicket_Failure_UserNotFound() {
        Ticket ticket = new Ticket();
        ticket.setTitle("Sample Ticket");
        ticket.setPriority(Priority.HIGH);
        ticket.setSeverity(Severity.HIGH);
        UserEntity user = new UserEntity();
        user.setId(1); 
        ticket.setUser(user);

        // Initialize the userrepo field to return an empty Optional
        when(userrepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.addTicket(ticket));
        verify(repo, times(0)).save(any(Ticket.class));
        verify(userrepo, times(1)).findById(anyInt());
    }

    @Test
    void testGetTicketById_TicketNotFound() {
        when(repo.findById(999)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getTicketById(999);
        });
        verify(repo, times(1)).findById(999);
    }

    @Test
    void testUpdateTicket_TicketNotFound() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(999);

        when(repo.findById(999)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> {
            service.updateTicket(ticket);
        });
        verify(repo, times(1)).findById(999);
        verify(repo, never()).save(any(Ticket.class));
    }

    @Test
    void testGetByPriority_NoTicketsFound() {
        Priority priority = Priority.HIGH;

        when(repo.findByPriority(priority)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getByPriority(priority);
        });
        verify(repo, times(1)).findByPriority(priority);
    }

    @Test
    void testGetBySeverity_NoTicketsFound() {
        Severity severity = Severity.HIGH;

        when(repo.findBySeverity(severity)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getBySeverity(severity);
        });
        verify(repo, times(1)).findBySeverity(severity);
    }

    @Test
    void testGetByUserEntity_NoTicketsFound() {
        UserEntity user = new UserEntity();

        when(repo.findByUser(user)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getByUserEntity(user);
        });
        verify(repo, times(1)).findByUser(user);
    }

    @Test
    void testGetAllTickets_NoTicketsFound() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getAllTickets();
        });
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetFourtyEightPlusAgedTickets_NoTicketsFound() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getFourtyEightPlusAgedTickets();
        });
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetTwentyFourPlusAgedTickets_NoTicketsFound() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.getTwentyFourPlusAgedTickets();
        });
        verify(repo, times(1)).findAll();
    }

    @Test
    void testHardwareIssue_NoTicketsFound() {
        when(repo.findByIssueType(IssueType.HARDWARE)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.hardwareIssue();
        });
        verify(repo, times(1)).findByIssueType(IssueType.HARDWARE);
    }

    @Test
    void testConnectivityIssue_NoTicketsFound() {
        when(repo.findByIssueType(IssueType.CONNECTIVITY)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.connectivityIssue();
        });
        verify(repo, times(1)).findByIssueType(IssueType.CONNECTIVITY);
    }

    @Test
    void testPerformanceIssue_NoTicketsFound() {
        when(repo.findByIssueType(IssueType.PERFORMANCE)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.performanceIssue();
        });
        verify(repo, times(1)).findByIssueType(IssueType.PERFORMANCE);
    }

    @Test
    void testAdministrativeIssue_NoTicketsFound() {
        when(repo.findByIssueType(IssueType.ADMINISTRATIVE)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.administrativeIssue();
        });
        verify(repo, times(1)).findByIssueType(IssueType.ADMINISTRATIVE);
    }

    @Test
    void testGoldTicketHandler_NoTicketsFound() {
        when(repo.findByCustomerTier(CustomerTier.GOLD)).thenReturn(Collections.emptyList());
        when(repo.findByStatus(Status.OPEN)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.goldTicketHandler();
        });
        verify(repo, times(1)).findByCustomerTier(CustomerTier.GOLD);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testSilverTicketHandler_NoTicketsFound() {
        when(repo.findByCustomerTier(CustomerTier.SILVER)).thenReturn(Collections.emptyList());
        when(repo.findByStatus(Status.OPEN)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.silverTicketHandler();
        });
        verify(repo, times(1)).findByCustomerTier(CustomerTier.SILVER);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testFindTicketsWithHighPriorityAndVariousSeverities_NoTicketsFound() {
        when(repo.findByPriority(Priority.HIGH)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.LOW)).thenReturn(Collections.emptyList());
        when(repo.findByStatus(Status.OPEN)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.findTicketsWithHighPriorityAndVariousSeverities();
        });
        verify(repo, times(1)).findByPriority(Priority.HIGH);
        verify(repo, times(1)).findBySeverity(Severity.HIGH);
        verify(repo, times(1)).findBySeverity(Severity.MEDIUM);
        verify(repo, times(1)).findBySeverity(Severity.LOW);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testFindTicketsWithMediumPriorityAndVariousSeverities_NoTicketsFound() {
        when(repo.findByPriority(Priority.MEDIUM)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.LOW)).thenReturn(Collections.emptyList());
        when(repo.findByStatus(Status.OPEN)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.findTicketsWithMediumPriorityAndVariousSeverities();
        });
        verify(repo, times(1)).findByPriority(Priority.MEDIUM);
        verify(repo, times(1)).findBySeverity(Severity.HIGH);
        verify(repo, times(1)).findBySeverity(Severity.MEDIUM);
        verify(repo, times(1)).findBySeverity(Severity.LOW);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }

    @Test
    void testFindTicketsWithLowPriorityAndVariousSeverities_NoTicketsFound() {
        when(repo.findByPriority(Priority.LOW)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.HIGH)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.MEDIUM)).thenReturn(Collections.emptyList());
        when(repo.findBySeverity(Severity.LOW)).thenReturn(Collections.emptyList());
        when(repo.findByStatus(Status.OPEN)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> {
            service.findTicketsWithLowPriorityAndVariousSeverities();
        });
        verify(repo, times(1)).findByPriority(Priority.LOW);
        verify(repo, times(1)).findBySeverity(Severity.HIGH);
        verify(repo, times(1)).findBySeverity(Severity.MEDIUM);
        verify(repo, times(1)).findBySeverity(Severity.LOW);
        verify(repo, times(1)).findByStatus(Status.OPEN);
    }
 
    @Test
    void testGetPriorityAnalysisReport_NoDataFound() throws TicketNotFoundException{
        List<Object[]> results = Collections.emptyList();
        when(repo.generatePriorityAnalysisReport()).thenReturn(results);
        
        List<PriorityAnalysisReport> result = service.getPriorityAnalysisReport();
        
        assertTrue(result.isEmpty());
        
        verify(repo, times(1)).generatePriorityAnalysisReport();
    }

    @Test
    void testGetTicketAgingReport_NoDataFound() {
        List<Object[]> results = Collections.emptyList();

        when(repo.generateTicketAgingReport()).thenReturn(results);

        assertThrows(TicketNotFoundException.class, () -> {
            service.getTicketAgingReport();
        });
        verify(repo, times(1)).generateTicketAgingReport();
    }

    @Test
    void testGetResolutionTimeReport_NoDataFound() throws TicketNotFoundException {
        List<Object[]> results = Collections.emptyList();
        when(repo.generateResolutionTimeReport()).thenReturn(results);
        
        List<ResolutionTimeReport> result = service.getResolutionTimeReport();

        assertTrue(result.isEmpty());  
        
        verify(repo, times(1)).generateResolutionTimeReport();
    }

}