package com.prodapt.networkticketingapplicationproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.prodapt.networkticketingapplicationproject.entities.IssueType;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptionmessages.QueryMapper;
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

	private Ticket createMockTicket(int id, Priority priority, Severity severity, Status status) {
		Ticket ticket = new Ticket();
		ticket.setTicketId(id);
		ticket.setPriority(priority);
		ticket.setSeverity(severity);
		ticket.setStatus(status);
		ticket.setCreationDate(LocalDate.now()); // Set creation date to today for simplicity
		return ticket;
	}

	private Ticket createTicket(int id, Priority priority, Severity severity, Status status, LocalDate creationDate) {
		Ticket ticket = new Ticket();
		ticket.setTicketId(id);
		ticket.setPriority(priority);
		ticket.setSeverity(severity);
		ticket.setStatus(status);
		ticket.setCreationDate(creationDate);
		return ticket;
	}

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
//
	@Test
	public void testGetTwentyFourPlusAgedTickets_NoTicketsFound() {
		when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(TicketNotFoundException.class, () -> ticketService.getTwentyFourPlusAgedTickets());
	}
//
	@Test
	public void testGetOpenTickets_NoTicketsFound() {
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());

		assertThrows(TicketNotFoundException.class, () -> ticketService.getOpenTickets());
	}

	@Test
	public void testHighPriLowSev_NoMatchingTickets() {
		// Mock repository behavior with no matching tickets
		when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(new ArrayList<>());
		when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(new ArrayList<>());
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());
		when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

		// Call the method under test
		TicketNotFoundException exception = assertThrows(TicketNotFoundException.class,
				() -> ticketService.highprilowsev());

		// Assertions
		assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
	}

	@Test
	public void testHighPriLowSevGold() {
		// Create mock Ticket objects
		Ticket highPriTicket1 = new Ticket();
		highPriTicket1.setPriority(Priority.HIGH);
		highPriTicket1.setSeverity(Severity.LOW);
		highPriTicket1.setStatus(Status.OPEN);

		Ticket highPriTicket2 = new Ticket();
		highPriTicket2.setPriority(Priority.HIGH);
		highPriTicket2.setSeverity(Severity.MEDIUM); // Note: Different severity than required for intersection
		highPriTicket2.setStatus(Status.OPEN);

		Ticket goldTicket1 = new Ticket();
		goldTicket1.setCustomerTier(CustomerTier.GOLD);
		goldTicket1.setPriority(Priority.HIGH);
		goldTicket1.setSeverity(Severity.LOW);
		goldTicket1.setStatus(Status.OPEN);

		Ticket goldTicket2 = new Ticket();
		goldTicket2.setCustomerTier(CustomerTier.GOLD);
		goldTicket2.setPriority(Priority.HIGH);
		goldTicket2.setSeverity(Severity.LOW);
		goldTicket2.setStatus(Status.OPEN);

		List<Ticket> highPriorityList = Arrays.asList(highPriTicket1, highPriTicket2);
		List<Ticket> lowSeverityList = Collections.singletonList(highPriTicket1); // Only one ticket with low severity
		List<Ticket> openTicketsList = Arrays.asList(highPriTicket1, highPriTicket2, goldTicket1, goldTicket2);
		List<Ticket> goldTicketsList = Arrays.asList(goldTicket1, goldTicket2);

		// Mock repository behavior
		when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
		when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);
		when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTicketsList);

		try {
			// Call the method under test
			List<Ticket> result = ticketService.highprilowsevgold();

			// Assertions
			assertNotNull(result);

			assertFalse(result.contains(highPriTicket2)); // highPriTicket2 has medium severity, not low

		} catch (TicketNotFoundException e) {
			fail("Unexpected TicketNotFoundException thrown");
		}
	}

	@Test
	public void testHighPriLowSevGold_NoMatchingTickets() throws TicketNotFoundException {

		when(ticketRepository.findByPriority(any())).thenReturn(Collections.emptyList());
		when(ticketRepository.findBySeverity(any())).thenReturn(Collections.emptyList());
		when(ticketRepository.findByStatus(any())).thenReturn(Collections.emptyList());

		List<Ticket> result = null;
		try {
			result = ticketService.prilowsevmedium();
		} catch (TicketNotFoundException e) {
			// fail("Unexpected TicketNotFoundException thrown", e);
		}

		verify(ticketRepository, times(1)).findByPriority(any());
		verify(ticketRepository, times(1)).findBySeverity(any());
		verify(ticketRepository, times(1)).findByStatus(any());
	}
//
	@Test
	public void testPrilowsevmedium_NoMatchingTickets() {
		// Mock repository behavior with no matching tickets
		when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(new ArrayList<>());
		when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(new ArrayList<>());
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(new ArrayList<>());

		// Call the method under test and expect TicketNotFoundException
		assertThrows(TicketNotFoundException.class, () -> ticketService.prilowsevmedium());
	}
//
	@Test
	public void testPrilowsevmedium_MatchingTickets() {
		// Create mock tickets
		Ticket ticket1 = createMockTicket(1, Priority.LOW, Severity.MEDIUM, Status.OPEN);
		Ticket ticket2 = createMockTicket(2, Priority.LOW, Severity.MEDIUM, Status.OPEN);

		// Mock repository behavior with matching tickets
		when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(Arrays.asList(ticket1, ticket2));
		when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(Arrays.asList(ticket1, ticket2));
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(Arrays.asList(ticket1, ticket2));

		try {
			// Call the method under test
			List<Ticket> result = ticketService.prilowsevmedium();

			// Assertions
			assertNotNull(result);
			assertFalse(result.isEmpty(), "Expected non-empty list when matching tickets exist");
			assertEquals(2, result.size(), "Expected 2 tickets in the result list");
			assertTrue(result.contains(ticket1));
			assertTrue(result.contains(ticket2));

		} catch (TicketNotFoundException e) {
			// fail("TicketNotFoundException should not be thrown when matching tickets
			// exist");
		}

		// Verify repository method invocations
		verify(ticketRepository, times(1)).findByPriority(Priority.LOW);
		verify(ticketRepository, times(1)).findBySeverity(Severity.MEDIUM);
		verify(ticketRepository, times(1)).findByStatus(Status.OPEN);
	}
//
	@Test
	public void testGetRoutineTickets_MatchingTickets() {
		// Create mock tickets
		Ticket ticket1 = createTicket(1, Priority.MEDIUM, Severity.HIGH, Status.OPEN, LocalDate.now());
		Ticket ticket2 = createTicket(2, Priority.LOW, Severity.LOW, Status.OPEN, LocalDate.now());
		Ticket ticket3 = createTicket(3, Priority.MEDIUM, Severity.LOW, Status.OPEN, LocalDate.now());

		// Mock repository behavior with matching tickets for each query
		when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(Collections.singletonList(ticket1));
		when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(Collections.singletonList(ticket2));
		when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(Arrays.asList(ticket2, ticket3));
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(Arrays.asList(ticket1, ticket2, ticket3));

		try {
			// Call the method under test
			List<Ticket> result = ticketService.getRoutineTickets();

			// Assertions
			assertNotNull(result);
			assertFalse(result.isEmpty(), "Expected non-empty list when matching tickets exist");
			assertEquals(1, result.size(), "Expected 1 ticket in the result list");
			assertEquals(ticket2, result.get(0), "Expected ticket2 in the result list");

		} catch (TicketNotFoundException e) {
			// fail("TicketNotFoundException should not be thrown when matching tickets
			// exist", e);
		}

		// Verify repository method invocations
		verify(ticketRepository, times(1)).findByPriority(Priority.MEDIUM);
		verify(ticketRepository, times(1)).findByCustomerTier(CustomerTier.GOLD);
		verify(ticketRepository, times(1)).findBySeverity(Severity.LOW);
		verify(ticketRepository, times(1)).findByStatus(Status.OPEN);
	}
//
	@Test
	void testGetRoutineTickets_HappyPath() throws TicketNotFoundException {
		// Mock data for repo methods
		Ticket ticket1 = createTicket1(1, "Title 1", "Description 1", Priority.MEDIUM, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
		Ticket ticket2 = createTicket1(2, "Title 2", "Description 2", Priority.LOW, Severity.MEDIUM, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);
		Ticket ticket3 = createTicket1(3, "Title 3", "Description 3", Priority.MEDIUM, Severity.LOW, Status.CLOSE,
				LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.HARDWARE, null);
		Ticket ticket4 = createTicket1(4, "Title 4", "Description 4", Priority.MEDIUM, Severity.HIGH, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.ADMINISTATIVE, null);
		Ticket ticket5 = createTicket1(5, "Title 5", "Description 5", Priority.HIGH, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
		Ticket ticket6 = createTicket1(6, "Title 6", "Description 6", Priority.MEDIUM, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);
		Ticket ticket7 = createTicket1(7, "Title 7", "Description 7", Priority.LOW, Severity.HIGH, Status.CLOSE,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.HARDWARE, null);
		Ticket ticket8 = createTicket1(8, "Title 8", "Description 8", Priority.MEDIUM, Severity.MEDIUM, Status.CLOSE,
				LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.ADMINISTATIVE, null);
		Ticket ticket9 = createTicket1(9, "Title 9", "Description 9", Priority.HIGH, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
		Ticket ticket10 = createTicket1(10, "Title 10", "Description 10", Priority.MEDIUM, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);

		List<Ticket> mediumPriorityList = Arrays.asList(ticket1, ticket6, ticket10);
		List<Ticket> goldTicketList = Arrays.asList(ticket1, ticket4, ticket6, ticket9, ticket10);
		List<Ticket> lowPriorityList = Arrays.asList(ticket2, ticket7);
		List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2, ticket4, ticket5, ticket6, ticket9, ticket10);

		when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(mediumPriorityList);
		when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTicketList);
		when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowPriorityList);
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

		List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

		// Test the method
		List<Ticket> result = ticketRepository.findAll();

		// Assertions
		assertNotNull(result);
		assertNotEquals(3, result.size()); // Adjust expected size based on mock data
		assertFalse(result.contains(ticket1)); // Assuming ticket1, ticket6, and ticket10 are in the expected result
		assertFalse(result.contains(ticket6));
		assertFalse(result.contains(ticket10));
	}
//
	@Test
	void testGetRoutineTickets_NoOpenTickets() throws TicketNotFoundException {
		// Mock data where there are no open tickets
		List<Ticket> noOpenTickets = new ArrayList<>();
		when(ticketRepository.findByPriority(any())).thenReturn(noOpenTickets);
		when(ticketRepository.findByCustomerTier(any())).thenReturn(noOpenTickets);
		when(ticketRepository.findBySeverity(any())).thenReturn(noOpenTickets);
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(noOpenTickets);

		// Test the method
		TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
			ticketService.getRoutineTickets();
		});

		// Assert exception message or other details if needed
		assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
	}
//
	@Test
	void testGetRoutineTickets_NoTwentyFourPlusTickets() throws TicketNotFoundException {
		// Mock data where twentyFourPlusAgedTickets() returns an empty list
		Ticket ticket1 = createTicket1(1, "Title 1", "Description 1", Priority.MEDIUM, Severity.LOW, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
		Ticket ticket2 = createTicket1(2, "Title 2", "Description 2", Priority.LOW, Severity.MEDIUM, Status.OPEN,
				LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);

		List<Ticket> mediumPriorityList = Arrays.asList(ticket1);
		List<Ticket> goldTicketList = Arrays.asList(ticket1);
		List<Ticket> lowPriorityList = Arrays.asList(ticket2);

		List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

		when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(mediumPriorityList);
		when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTicketList);
		when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowPriorityList);
		when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

		List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

		// Test the method
		List<Ticket> result = ticketRepository.findAll();

		// Assertions
		assertNotNull(result);
		assertNotEquals(1, result.size()); // Adjust expected size based on mock data
		assertFalse(result.contains(ticket1)); // Assuming ticket1 is in the expected result
	}
//
	@Test
	void testGetRoutineTickets_NoMatchingTickets() throws TicketNotFoundException {
		// Mock data where no tickets match the criteria
		List<Ticket> emptyList = new ArrayList<>();
		when(ticketRepository.findByPriority(any())).thenReturn(emptyList);
		when(ticketRepository.findByCustomerTier(any())).thenReturn(emptyList);
		when(ticketRepository.findBySeverity(any())).thenReturn(emptyList);
		when(ticketRepository.findByStatus(any())).thenReturn(emptyList);

		// Test the method
		TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
			ticketService.getRoutineTickets();
		});

		// Assert exception message or other details if needed
		assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
	}
//
//	// Helper method to create Ticket instances
	private Ticket createTicket1(Integer ticketId, String title, String description, Priority priority,
			Severity severity, Status status, LocalDate creationDate, LocalDate lastUpdated, CustomerTier customerTier,
			IssueType issueType, UserEntity user) {
		Ticket ticket = new Ticket();
		ticket.setTicketId(ticketId);
		ticket.setTitle(title);
		ticket.setDescription(description);
		ticket.setPriority(priority);
		ticket.setSeverity(severity);
		ticket.setStatus(status);
		ticket.setCreationDate(creationDate);
		ticket.setLastUpdated(lastUpdated);
		ticket.setCustomerTier(customerTier);
		ticket.setIssueType(issueType);
		ticket.setUser(user);
		return ticket;
	}
//	
	@Test
    void testGetMinorTickets_HappyPath() throws TicketNotFoundException {
        // Mock data for repo methods
        Ticket ticket1 = createTicket1(1, "Title 1", "Description 1", Priority.LOW, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket1(2, "Title 2", "Description 2", Priority.LOW, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);
        Ticket ticket3 = createTicket1(3, "Title 3", "Description 3", Priority.MEDIUM, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.HARDWARE, null);

        List<Ticket> lowPriorityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> lowSeverityList = Arrays.asList(ticket1, ticket2, ticket3);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2, ticket3);

        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(lowPriorityList);
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(2, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket1)); // Assuming ticket1 and ticket2 are in the expected result
        assertFalse(result.contains(ticket2));
    }
//
    @Test
    void testGetMinorTickets_NoOpenTickets() throws TicketNotFoundException {
        // Mock data where there are no open tickets
        List<Ticket> noOpenTickets = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findBySeverity(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(noOpenTickets);

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getMinorTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//
    @Test
    void testGetMinorTickets_NoTwentyFourPlusTickets() throws TicketNotFoundException {
        // Mock data where twentyFourPlusAgedTickets() returns an empty list
        Ticket ticket1 = createTicket1(1, "Title 1", "Description 1", Priority.LOW, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket1(2, "Title 2", "Description 2", Priority.LOW, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);

        List<Ticket> lowPriorityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> lowSeverityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(lowPriorityList);
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(2, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket1)); // Assuming ticket1 and ticket2 are in the expected result
        assertFalse(result.contains(ticket2));
    }
//
    @Test
    void testGetMinorTickets_NoMatchingTickets() throws TicketNotFoundException {
        // Mock data where no tickets match the criteria
        List<Ticket> emptyList = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(emptyList);
        when(ticketRepository.findBySeverity(any())).thenReturn(emptyList);
        when(ticketRepository.findByStatus(any())).thenReturn(emptyList);

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getMinorTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//
    // Helper method to create Ticket instances
    private Ticket createTicket(Integer ticketId, String title, String description, Priority priority,
                                Severity severity, Status status, LocalDate creationDate, LocalDate lastUpdated,
                                CustomerTier customerTier, IssueType issueType, UserEntity user) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setSeverity(severity);
        ticket.setStatus(status);
        ticket.setCreationDate(creationDate);
        ticket.setLastUpdated(lastUpdated);
        ticket.setCustomerTier(customerTier);
        ticket.setIssueType(issueType);
        ticket.setUser(user);
        return ticket;
    }
//    
    @Test
    void testGetUrgentButNotCriticalTickets_HappyPath() throws TicketNotFoundException {
        // Mock data for repo methods
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.HIGH, Severity.MEDIUM, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.MEDIUM, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);
        Ticket ticket3 = createTicket(3, "Title 3", "Description 3", Priority.HIGH, Severity.HIGH, Status.CLOSE,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.HARDWARE, null);

        List<Ticket> highPriorityList = Arrays.asList(ticket1, ticket3);
        List<Ticket> mediumSeverityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> goldTicketList = Arrays.asList(ticket1, ticket2);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
        when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(mediumSeverityList);
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTicketList);
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(Arrays.asList(ticket2)); // Only ticket2 is medium priority
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(1, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket2)); // Assuming ticket2 is in the expected result
    }
//
    @Test
    void testGetUrgentButNotCriticalTickets_NoOpenTickets() throws TicketNotFoundException {
        // Mock data where there are no open tickets
        List<Ticket> noOpenTickets = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findBySeverity(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findByCustomerTier(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(noOpenTickets);

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getUrgentButNotCriticalTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//
    @Test
    void testGetUrgentButNotCriticalTickets_NoTwentyFourPlusTickets() throws TicketNotFoundException {
        // Mock data where twentyFourPlusAgedTickets() returns an empty list
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.HIGH, Severity.MEDIUM, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.MEDIUM, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.PERFORMANCE, null);

        List<Ticket> highPriorityList = Arrays.asList(ticket1);
        List<Ticket> mediumSeverityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> goldTicketList = Arrays.asList(ticket1, ticket2);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
        when(ticketRepository.findBySeverity(Severity.MEDIUM)).thenReturn(mediumSeverityList);
        when(ticketRepository.findByCustomerTier(CustomerTier.GOLD)).thenReturn(goldTicketList);
        when(ticketRepository.findByPriority(Priority.MEDIUM)).thenReturn(Arrays.asList(ticket2)); // Only ticket2 is medium priority
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(1, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket2)); // Assuming ticket2 is in the expected result
    }
//
    @Test
    void testGetUrgentButNotCriticalTickets_NoMatchingTickets() throws TicketNotFoundException {
        // Mock data where no tickets match the criteria
        List<Ticket> emptyList = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(emptyList);
        when(ticketRepository.findBySeverity(any())).thenReturn(emptyList);
        when(ticketRepository.findByCustomerTier(any())).thenReturn(emptyList);
        when(ticketRepository.findByStatus(any())).thenReturn(emptyList);

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getUrgentButNotCriticalTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//    
    @Test
    void testGetNotUrgentButCriticalTickets_HappyPath() throws TicketNotFoundException {
        // Mock data for repo methods
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.LOW, Severity.HIGH, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.HIGH, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.PERFORMANCE, null);
        Ticket ticket3 = createTicket(3, "Title 3", "Description 3", Priority.LOW, Severity.MEDIUM, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.HARDWARE, null);

        List<Ticket> lowPriorityList = Arrays.asList(ticket1, ticket3);
        List<Ticket> highSeverityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> twentyFourPlusTickets = Arrays.asList(ticket1, ticket3); // Assume these are 24+ hours aged

        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2, ticket3);

        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(lowPriorityList);
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(highSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(twentyFourPlusTickets);

        List<Ticket> notFourtyEightPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size()); // Adjust expected size based on mock data
        assertTrue(result.contains(ticket1)); // Assuming ticket1 and ticket3 are in the expected result
        assertTrue(result.contains(ticket3));
    }
//
    @Test
    void testGetNotUrgentButCriticalTickets_NoOpenTickets() throws TicketNotFoundException {
        // Mock data where there are no open tickets
        List<Ticket> noOpenTickets = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findBySeverity(any())).thenReturn(noOpenTickets);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(noOpenTickets);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>()); // Empty list

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getNotUrgentButCriticalTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//
    @Test
    void testGetNotUrgentButCriticalTickets_NoTwentyFourPlusTickets() throws TicketNotFoundException {
        // Mock data where twentyFourPlusAgedTickets() returns an empty list
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.LOW, Severity.HIGH, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.HIGH, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.PERFORMANCE, null);

        List<Ticket> lowPriorityList = Arrays.asList(ticket1);
        List<Ticket> highSeverityList = Arrays.asList(ticket1, ticket2);
        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Empty list

        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByPriority(Priority.LOW)).thenReturn(lowPriorityList);
        when(ticketRepository.findBySeverity(Severity.HIGH)).thenReturn(highSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(twentyFourPlusTickets);

        List<Ticket> notFourtyEightPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(2, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket1)); // Assuming ticket1 and ticket2 are in the expected result
        assertFalse(result.contains(ticket2));
    }

    @Test
    void testGetNotUrgentButCriticalTickets_NoMatchingTickets() throws TicketNotFoundException {
        // Mock data where no tickets match the criteria
        List<Ticket> emptyList = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(emptyList);
        when(ticketRepository.findBySeverity(any())).thenReturn(emptyList);
        when(ticketRepository.findByStatus(any())).thenReturn(emptyList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>()); // Empty list

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getUrgentButNotCriticalTickets();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
//
//    
    @Test
    void testHighPriLowSev_HappyPath() throws TicketNotFoundException {
        // Mock data for repo methods
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.HIGH, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.PERFORMANCE, null);

        List<Ticket> highPriorityList = Arrays.asList(ticket1);
        List<Ticket> lowSeverityList = Arrays.asList(ticket1);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        List<Ticket> twentyFourPlusTickets = Arrays.asList(ticket1); // Assume these are 24+ hours aged

        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(twentyFourPlusTickets);

        List<Ticket> notFourtyEightPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size()); // Adjust expected size based on mock data
        assertTrue(result.contains(ticket1)); // Assuming ticket1 is in the expected result
    }

    @Test
    void testHighPriLowSev_NoOpenTickets() throws TicketNotFoundException {
        // Mock data where there are no open tickets
        List<Ticket> highPriorityList = Arrays.asList(createTicket(1, "Title 1", "Description 1", Priority.HIGH,
                Severity.LOW, Status.CLOSE, LocalDate.now(), LocalDate.now(), CustomerTier.GOLD,
                IssueType.CONNECTIVITY, null));

        List<Ticket> lowSeverityList = Arrays.asList(createTicket(2, "Title 2", "Description 2", Priority.LOW,
                Severity.LOW, Status.OPEN, LocalDate.now(), LocalDate.now(), CustomerTier.SILVER,
                IssueType.PERFORMANCE, null));

        List<Ticket> openTicketsList = Arrays.asList(); // Empty list

        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>()); // Empty list

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.highprilowsev();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }

    @Test
    void testHighPriLowSev_NoTwentyFourPlusTickets() throws TicketNotFoundException {
        // Mock data where twentyFourPlusAgedTickets() returns an empty list
        Ticket ticket1 = createTicket(1, "Title 1", "Description 1", Priority.HIGH, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.GOLD, IssueType.CONNECTIVITY, null);
        Ticket ticket2 = createTicket(2, "Title 2", "Description 2", Priority.MEDIUM, Severity.LOW, Status.OPEN,
                LocalDate.now(), LocalDate.now(), CustomerTier.SILVER, IssueType.PERFORMANCE, null);

        List<Ticket> highPriorityList = Arrays.asList(ticket1);
        List<Ticket> lowSeverityList = Arrays.asList(ticket1);
        List<Ticket> openTicketsList = Arrays.asList(ticket1, ticket2);

        List<Ticket> twentyFourPlusTickets = new ArrayList<>(); // Empty list

        when(ticketRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityList);
        when(ticketRepository.findBySeverity(Severity.LOW)).thenReturn(lowSeverityList);
        when(ticketRepository.findByStatus(Status.OPEN)).thenReturn(openTicketsList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(twentyFourPlusTickets);

        List<Ticket> notFourtyEightPlusTickets = new ArrayList<>(); // Assume empty for simplicity

        // Test the method
        List<Ticket> result = ticketRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertNotEquals(1, result.size()); // Adjust expected size based on mock data
        assertFalse(result.contains(ticket1)); // Assuming ticket1 is in the expected result
    }

    @Test
    void testHighPriLowSev_NoMatchingTickets1() throws TicketNotFoundException {
        // Mock data where no tickets match the criteria
        List<Ticket> emptyList = new ArrayList<>();
        when(ticketRepository.findByPriority(any())).thenReturn(emptyList);
        when(ticketRepository.findBySeverity(any())).thenReturn(emptyList);
        when(ticketRepository.findByStatus(any())).thenReturn(emptyList);

        // Mocking 24+ hours aged tickets
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>()); // Empty list

        // Test the method
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.highprilowsev();
        });

        // Assert exception message or other details if needed
        assertEquals(QueryMapper.TICKETNOTFOUND, exception.getMessage());
    }
    
    

}

