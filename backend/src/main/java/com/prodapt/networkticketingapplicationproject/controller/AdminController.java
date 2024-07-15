package com.prodapt.networkticketingapplicationproject.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.model.PriorityAnalysisReport;
import com.prodapt.networkticketingapplicationproject.model.ResolutionTimeReport;
import com.prodapt.networkticketingapplicationproject.model.TicketAgingReport;
import com.prodapt.networkticketingapplicationproject.requestentities.AdminUpdateCustomerTier;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.GetUserById;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateAdmin;
import com.prodapt.networkticketingapplicationproject.requestentities.UpdateUserRoleRequest;
import com.prodapt.networkticketingapplicationproject.service.RoleService;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
	private static final Logger loggers = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserEntityService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private TicketService ticketService;

	@GetMapping("/getalltickets")
	public ResponseEntity<List<Ticket>> getAll() throws TicketNotFoundException {
		List<Ticket> tickets = ticketService.getAllTickets();
		loggers.info("getall");
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}

	@GetMapping("/getallusers")
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		List<UserEntity> users = userService.getAll();
		return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);

	}

	@PostMapping("/updateuserrole")
	public ResponseEntity<String> updateUserRole(@RequestBody UpdateUserRoleRequest updateUserRoleRequest)
			throws RoleNotFoundException {

		// Find the role by name
		Optional<Role> r = roleService.findRoleByName(updateUserRoleRequest.getRole());

		// Update the user role
		String message = userService.updaterole(updateUserRoleRequest.getUsername(), r.get());
		loggers.info("Role Updated Successfully");
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}

	@PostMapping("/getticket")
	public ResponseEntity<Ticket> getTicket(@RequestBody GetByTicketId getByTicketId) throws TicketNotFoundException {
		Ticket t = ticketService.getTicketById(getByTicketId.getTicketId());
		loggers.info("getticket");
		return new ResponseEntity<Ticket>(t, HttpStatus.OK);
	}
	

	
	@PostMapping("/updatetierofuser")
	public ResponseEntity<UserEntity> UpdateTier(@RequestBody AdminUpdateCustomerTier req){
		Optional<UserEntity>  usertier= userService.findByUsername(req.getUsername());
		
		
		usertier.get().setTier(req.getCustomertier());
	
		
		UserEntity userentity=userService.addUserEntity(usertier.get());
		
		return new ResponseEntity<UserEntity>(userentity, HttpStatus.OK);
	}

	@PostMapping("/allotpriorityandseverity")
	public ResponseEntity<Ticket> updateTicket(@RequestBody TicketUpdateAdmin req) throws TicketNotFoundException
	{

		Ticket ticket = ticketService.getTicketById(req.getTicketId());

		if (ticket.getCustomerTier() == CustomerTier.GOLD) {
			
				ticket.setPriority(Priority.HIGH);
				ticket.setSeverity(req.getSeverity());
				ticket.setLastUpdated(LocalDate.now());
				Ticket updatedTicket = ticketService.updateTicket(ticket);
				return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
			}
    else {
		   if(ticket.getCustomerTier() == CustomerTier.SILVER) {
			
			  if (req.getPriority() == Priority.LOW)
			     {
				ticket.setSeverity(req.getSeverity());
				ticket.setPriority(Priority.MEDIUM);
				ticket.setLastUpdated(LocalDate.now());
				Ticket updatedTicket = ticketService.updateTicket(ticket);
				return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
			    }
			   else if (req.getPriority() == Priority.MEDIUM) {
				ticket.setPriority(Priority.HIGH);
				ticket.setSeverity(req.getSeverity());
				ticket.setLastUpdated(LocalDate.now());
				Ticket updatedTicket = ticketService.updateTicket(ticket);
				return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
			    }
			   else {
				ticket.setSeverity(req.getSeverity());
				ticket.setPriority(req.getPriority());
				ticket.setLastUpdated(LocalDate.now());
				Ticket updatedTicket = ticketService.updateTicket(ticket);
				return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
			}

		}
		   else {
			ticket.setPriority(req.getPriority());
			ticket.setSeverity(req.getSeverity());
			ticket.setLastUpdated(LocalDate.now());
			Ticket updatedTicket = ticketService.updateTicket(ticket);
			return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
		}
    }
		
		
	}
	
    @GetMapping("/ticketAgingReport")
    public ResponseEntity<List<TicketAgingReport>> getTicketAgingReport() throws TicketNotFoundException {
        List<TicketAgingReport> report = ticketService.getTicketAgingReport();
        if (report != null && !report.isEmpty()) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/resolutionTimeReport")
    public ResponseEntity<List<ResolutionTimeReport>> getResolutionTimeReport() throws TicketNotFoundException {
        List<ResolutionTimeReport> report = ticketService.getResolutionTimeReport();
        if (report != null && !report.isEmpty()) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    @GetMapping("/priorityAnalysisReport")
    public ResponseEntity<List<PriorityAnalysisReport>> getPriorityAnalysisReport() throws TicketNotFoundException {
        List<PriorityAnalysisReport> report = ticketService.getPriorityAnalysisReport();
        if (report != null && !report.isEmpty()) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
    
    

}