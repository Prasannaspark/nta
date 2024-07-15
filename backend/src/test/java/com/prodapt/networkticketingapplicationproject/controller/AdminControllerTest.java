package com.prodapt.networkticketingapplicationproject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.ERole;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.model.PriorityAnalysisReport;
import com.prodapt.networkticketingapplicationproject.model.ResolutionTimeReport;
import com.prodapt.networkticketingapplicationproject.model.TicketAgingReport;
import com.prodapt.networkticketingapplicationproject.requestentities.AdminUpdateCustomerTier;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateAdmin;
import com.prodapt.networkticketingapplicationproject.requestentities.UpdateUserRoleRequest;
import com.prodapt.networkticketingapplicationproject.service.RoleService;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserEntityService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private AdminController adminController;
    
    @Test
    void testGetAllSuccess() throws TicketNotFoundException {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAllTickets()).thenReturn(tickets);

        ResponseEntity<List<Ticket>> response = adminController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllFailure() throws TicketNotFoundException {
        when(ticketService.getAllTickets()).thenThrow(new TicketNotFoundException("No tickets found"));

        assertThrows(TicketNotFoundException.class, () -> {
            adminController.getAll();
        });
    }

    @Test
    void testGetAllUsersSuccess() {
        List<UserEntity> users = Arrays.asList(new UserEntity(), new UserEntity());
        when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<UserEntity>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testUpdateUserRoleSuccess() throws RoleNotFoundException {
        UpdateUserRoleRequest request = new UpdateUserRoleRequest("username", ERole.ROLE_USER);
        Role role = new Role();
        when(roleService.findRoleByName(request.getRole())).thenReturn(Optional.of(role));
        when(userService.updaterole(request.getUsername(), role)).thenReturn("Role updated successfully");

        ResponseEntity<String> response = adminController.updateUserRole(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Role updated successfully", response.getBody());
    }

    @Test
    void testUpdateUserRoleFailure() throws RoleNotFoundException {
        UpdateUserRoleRequest request = new UpdateUserRoleRequest("username", ERole.ROLE_USER);
        when(roleService.findRoleByName(request.getRole())).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> {
            try {
                adminController.updateUserRole(request);
            } catch (NoSuchElementException e) {
                throw new RoleNotFoundException("Role not found");
            }
        });
    }


    @Test
    void testGetTicketSuccess() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId(1);
        Ticket ticket = new Ticket();
        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);

        ResponseEntity<Ticket> response = adminController.getTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testGetTicketFailure() throws TicketNotFoundException {
        GetByTicketId request = new GetByTicketId(1);
        when(ticketService.getTicketById(request.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            adminController.getTicket(request);
        });
    }

    @Test
    void testUpdateTicketSuccess_GoldCustomer() throws TicketNotFoundException {
        TicketUpdateAdmin request = new TicketUpdateAdmin(1, Priority.HIGH, Severity.HIGH);
        Ticket ticket = new Ticket();
        ticket.setCustomerTier(CustomerTier.GOLD);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Ticket> response = adminController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        assertEquals(request.getSeverity(), ticket.getSeverity());
        assertEquals(request.getPriority(), ticket.getPriority());
        assertEquals(LocalDate.now(), ticket.getLastUpdated());
    }

    @Test
    void testUpdateTicketSuccess_SilverCustomerLowPriority() throws TicketNotFoundException {
        TicketUpdateAdmin request = new TicketUpdateAdmin(1, Priority.LOW, Severity.HIGH);
        Ticket ticket = new Ticket();
        ticket.setCustomerTier(CustomerTier.SILVER);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = adminController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testUpdateTicketSuccess_SilverCustomerMediumPriority() throws TicketNotFoundException {
        TicketUpdateAdmin request = new TicketUpdateAdmin(1, Priority.MEDIUM, Severity.HIGH);
        Ticket ticket = new Ticket();
        ticket.setCustomerTier(CustomerTier.SILVER);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = adminController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void testUpdateTicketSuccess_BronzeCustomer() throws TicketNotFoundException {
        TicketUpdateAdmin request = new TicketUpdateAdmin(1, Priority.HIGH, Severity.HIGH);
        Ticket ticket = new Ticket();
        ticket.setCustomerTier(CustomerTier.BRONZE);

        when(ticketService.getTicketById(request.getTicketId())).thenReturn(ticket);
        when(ticketService.updateTicket(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Ticket> response = adminController.updateTicket(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        assertEquals(request.getSeverity(), ticket.getSeverity());
        assertEquals(request.getPriority(), ticket.getPriority());
        assertEquals(LocalDate.now(), ticket.getLastUpdated());
    }

    @Test
    void testUpdateTicketFailure_TicketNotFound() throws TicketNotFoundException {
        TicketUpdateAdmin request = new TicketUpdateAdmin(1, Priority.HIGH, Severity.HIGH);
        when(ticketService.getTicketById(request.getTicketId())).thenThrow(new TicketNotFoundException("Ticket not found"));

        assertThrows(TicketNotFoundException.class, () -> {
            adminController.updateTicket(request);
        });
    }

    @Test
    void testGetTicketAgingReportSuccess() throws TicketNotFoundException {
        List<TicketAgingReport> report = Arrays.asList(new TicketAgingReport(), new TicketAgingReport());
        when(ticketService.getTicketAgingReport()).thenReturn(report);

        ResponseEntity<List<TicketAgingReport>> response = adminController.getTicketAgingReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetTicketAgingReportFailure() throws TicketNotFoundException {
        when(ticketService.getTicketAgingReport()).thenReturn(null);

        ResponseEntity<List<TicketAgingReport>> response = adminController.getTicketAgingReport();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetResolutionTimeReportSuccess() throws TicketNotFoundException {
        List<ResolutionTimeReport> report = Arrays.asList(new ResolutionTimeReport(), new ResolutionTimeReport());
        when(ticketService.getResolutionTimeReport()).thenReturn(report);

        ResponseEntity<List<ResolutionTimeReport>> response = adminController.getResolutionTimeReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetResolutionTimeReportFailure() throws TicketNotFoundException {
        when(ticketService.getResolutionTimeReport()).thenReturn(null);

        ResponseEntity<List<ResolutionTimeReport>> response = adminController.getResolutionTimeReport();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetPriorityAnalysisReportSuccess() throws TicketNotFoundException {
        List<PriorityAnalysisReport> report = Arrays.asList(new PriorityAnalysisReport(), new PriorityAnalysisReport());
        when(ticketService.getPriorityAnalysisReport()).thenReturn(report);

        ResponseEntity<List<PriorityAnalysisReport>> response = adminController.getPriorityAnalysisReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetPriorityAnalysisReportFailure() throws TicketNotFoundException {
        when(ticketService.getPriorityAnalysisReport()).thenReturn(null);

        ResponseEntity<List<PriorityAnalysisReport>> response = adminController.getPriorityAnalysisReport();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testUpdateTier_Success() {
        AdminUpdateCustomerTier req = new AdminUpdateCustomerTier();
        req.setUsername("username");
        req.setCustomertier(CustomerTier.GOLD);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setTier(CustomerTier.BRONZE);

        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);

        when(userService.findByUsername(req.getUsername())).thenReturn(optionalUserEntity);
        when(userService.addUserEntity(userEntity)).thenReturn(userEntity);

        ResponseEntity<UserEntity> response = adminController.UpdateTier(req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CustomerTier.GOLD, response.getBody().getTier());
        verify(userService, times(1)).findByUsername(req.getUsername());
        verify(userService, times(1)).addUserEntity(userEntity);
    }
    
    @Test
    void testUpdateTier_Failure_UserNotFound() {
        AdminUpdateCustomerTier req = new AdminUpdateCustomerTier();
        req.setUsername("username");
        req.setCustomertier(CustomerTier.GOLD);

        when(userService.findByUsername(req.getUsername())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> adminController.UpdateTier(req));
        verify(userService, times(1)).findByUsername(req.getUsername());
    }
}
