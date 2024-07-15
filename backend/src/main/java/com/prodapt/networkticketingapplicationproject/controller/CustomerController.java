package com.prodapt.networkticketingapplicationproject.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.GetCustomerTickets;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketRequest;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateRequest;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins="*", maxAge=3600)
public class CustomerController {
	private static final Logger loggers = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	UserEntityService userEntityService;
	
	@Autowired
	TicketService ticketService;
	
	@PostMapping("/addticket")
	public ResponseEntity<Ticket> addTicket(@RequestBody TicketRequest requestTicket){
		Ticket ticket=new Ticket();
		ticket.setTitle(requestTicket.getTitle());
		ticket.setDescription(requestTicket.getDescription());
		ticket.setIssueType(requestTicket.getIssueType());
		Optional<UserEntity> user= userEntityService.findByUsername(requestTicket.getUsername());
		ticket.setUser(user.get());
		Ticket t= ticketService.addTicket(ticket);
		loggers.info("addticket");
		return new ResponseEntity<>(t,HttpStatus.OK);
	}
	
	@PostMapping("/updateticket")
	public ResponseEntity<Ticket> updateTicket(@RequestBody TicketUpdateRequest requestTicket) throws TicketNotFoundException{
		Ticket ticket=ticketService.getTicketById(requestTicket.getTicketId());
		ticket.setTitle(requestTicket.getTitle());
		ticket.setDescription(requestTicket.getDescription());
		ticket.setIssueType(requestTicket.getIssueType());
		Ticket t= ticketService.updateTicket(ticket);
		loggers.info("updateticket");
		return new ResponseEntity<>(t,HttpStatus.OK);
	}
	
	@PostMapping("/getticket")
	public ResponseEntity<Ticket> getTicket(@RequestBody GetByTicketId getByTicketId) throws TicketNotFoundException{
		Ticket t= ticketService.getTicketById(getByTicketId.getTicketId());
		loggers.info("getticket");
		return new ResponseEntity<>(t,HttpStatus.OK);
	}
	
	@PostMapping("/getmytickets")
	public ResponseEntity<List<Ticket>> getMyTicket(@RequestBody GetCustomerTickets getCustomerTicket) throws TicketNotFoundException{
		Optional<UserEntity> user=userEntityService.findByUsername(getCustomerTicket.getUsername());
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ticket> t= ticketService.getByUserEntity(user.get());
		loggers.info("getmytickets");
		return new ResponseEntity<>(t,HttpStatus.OK);
	
	}
	
}
