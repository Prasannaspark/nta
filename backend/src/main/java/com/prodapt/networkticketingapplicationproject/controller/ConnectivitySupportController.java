package com.prodapt.networkticketingapplicationproject.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.prodapt.networkticketingapplicationproject.exceptionmessages.QueryMapper;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.GetByTicketId;
import com.prodapt.networkticketingapplicationproject.requestentities.TicketUpdateIT;
import com.prodapt.networkticketingapplicationproject.service.TicketService;

@RestController
@RequestMapping("/api/connectivitysupport")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConnectivitySupportController {

	@Autowired
	TicketService ticketservice;

	@PostMapping("/updateticket")
	public ResponseEntity<Ticket> updateTicket(@RequestBody TicketUpdateIT req) throws TicketNotFoundException {
		Ticket ticket = ticketservice.getTicketById(req.getTicketId());
		ticket.setStatus(req.getStatus());
		ticket.setLastUpdated(LocalDate.now());
		Ticket updatedTicket = ticketservice.updateTicket(ticket);
		return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
	}
	

	@PostMapping("/getticket")
	public ResponseEntity<Ticket> getTicket(@RequestBody GetByTicketId getByTicketId) throws TicketNotFoundException {
		Ticket t = ticketservice.getTicketById(getByTicketId.getTicketId());
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@GetMapping("/lowprioritytickets")
	public ResponseEntity<List<Ticket>> getConnectivityLowPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithLowPriorityAndVariousSeverities();
		List<Ticket> conectionissue = ticketservice.connectivityIssue();
        Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> conectionissueSet = new HashSet<>(conectionissue);
		unionSet.retainAll(conectionissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if(intersectionList.isEmpty()) throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}
	
	@GetMapping("/mediumprioritytickets")
	public ResponseEntity<List<Ticket>> getConnectivityMediumPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithMediumPriorityAndVariousSeverities();
		List<Ticket> conectionissue = ticketservice.connectivityIssue();
        Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> conectionissueSet = new HashSet<>(conectionissue);
		unionSet.retainAll(conectionissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if(intersectionList.isEmpty()) throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}
	
	@GetMapping("/highprioritytickets")
	public ResponseEntity<List<Ticket>> getConnectivityHighPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithHighPriorityAndVariousSeverities();
		List<Ticket> conectionissue = ticketservice.connectivityIssue();
        Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> conectionissueSet = new HashSet<>(conectionissue);
		unionSet.retainAll(conectionissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if(intersectionList.isEmpty()) throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}
	
	@GetMapping("/getcriticalticket")
	public ResponseEntity<List<Ticket>> getCriticalTicket() throws TicketNotFoundException {
		List<Ticket> connectivityissue = ticketservice.connectivityIssue();
		List<Ticket> tickets= ticketservice.getFourtyEightPlusAgedTickets();
		if(!tickets.isEmpty())
		{
		List<Ticket> intersection = connectivityissue.stream()
		        .filter(tickets::contains) // Filter tickets in connectivityissue that are also in tickets
		        .collect(Collectors.toList());
		if(!intersection.isEmpty())
		{
		return new ResponseEntity<>(intersection, HttpStatus.OK);
		}
		else throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
		else throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

}
