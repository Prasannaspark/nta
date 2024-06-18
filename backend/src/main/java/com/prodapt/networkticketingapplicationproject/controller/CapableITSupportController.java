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
@RequestMapping("/api/capable")
@CrossOrigin(origins="*", maxAge=3600)
public class CapableITSupportController {
	private static final Logger loggers = LoggerFactory.getLogger(CapableITSupportController.class);

	@Autowired
	TicketService ticketService;

	@GetMapping("/urgentbutnotsevere")
	public ResponseEntity<List<Ticket>> getUrgentButNotCriticalTicket() throws TicketNotFoundException {
		List<Ticket> tP = ticketService.getUrgentButNotCriticalTickets();
		List<Ticket> hpls=ticketService.highprilowsev(); 
		
		
	List<Ticket> combinedList = Stream.concat(tP.stream(), hpls.stream())
     .collect(Collectors.toList());

		loggers.info("urgentbutnotsevere");
		return new ResponseEntity<>(combinedList, HttpStatus.OK);
	}

	@GetMapping("/noturgentbutcritical")
	public ResponseEntity<List<Ticket>> getNotUrgentButCriticalTicket() throws TicketNotFoundException {
		List<Ticket> tP = ticketService.getNotUrgentButCriticalTickets();
		loggers.info("noturgentbutcritical");
		return new ResponseEntity<>(tP, HttpStatus.OK);
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
