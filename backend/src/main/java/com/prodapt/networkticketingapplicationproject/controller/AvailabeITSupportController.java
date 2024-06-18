package com.prodapt.networkticketingapplicationproject.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateIT;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
@RestController
@RequestMapping("/api/available")
@CrossOrigin(origins="*", maxAge=3600)
public class AvailabeITSupportController {
	private static final Logger loggers = LoggerFactory.getLogger(AvailabeITSupportController.class);

	@Autowired
	TicketService ticketService;
	
	@GetMapping("/routine")
	public ResponseEntity<List<Ticket>> getRoutineTicket() throws TicketNotFoundException{
		List<Ticket> t=ticketService.getRoutineTickets();
		List<Ticket> plsm=ticketService.prilowsevmedium();
		
		
		
		List<Ticket> combinedList = Stream.concat(t.stream(), plsm.stream())
                .collect(Collectors.toList());
		
		loggers.info("routine");
		return new ResponseEntity<>(combinedList,HttpStatus.OK);
	}
	
	@GetMapping("/minor")
	public ResponseEntity<List<Ticket>> getMinorTicket() throws TicketNotFoundException{
		List<Ticket> t =ticketService.getMinorTickets();
		loggers.info("minor");
		return new ResponseEntity<>(t,HttpStatus.OK);
	}
	
	@PostMapping("/updateticket")
	public ResponseEntity<Ticket> updateTicket(@RequestBody TicketUpdateIT req) throws TicketNotFoundException
	{
		Ticket ticket= ticketService.getTicketById(req.getTicketId());
		ticket.setStatus(req.getStatus());
		ticket.setLastUpdated(LocalDate.now());
		Ticket updatedTicket=ticketService.updateTicket(ticket);
		return new ResponseEntity<>(updatedTicket,HttpStatus.OK);
	}
	
	@PostMapping("/getticket")
	public ResponseEntity<Ticket> getTicket(@RequestBody GetByTicketId getByTicketId) throws TicketNotFoundException{
		Ticket t= ticketService.getTicketById(getByTicketId.getTicketId());
		loggers.info("getticket");
		return new ResponseEntity<Ticket>(t,HttpStatus.OK);
	}

}
