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
@RequestMapping("/api/performancesupport")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PerformanceSupportController {

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
	public ResponseEntity<List<Ticket>> getPerformanceLowPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithLowPriorityAndVariousSeverities();
		List<Ticket> performanceissue = ticketservice.performanceIssue();
		Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> performanceissueSet = new HashSet<>(performanceissue);
		unionSet.retainAll(performanceissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if (intersectionList.isEmpty())
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else
			return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}

	@GetMapping("/mediumprioritytickets")
	public ResponseEntity<List<Ticket>> getPerformanceMediumPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithMediumPriorityAndVariousSeverities();
		List<Ticket> performanceissue = ticketservice.performanceIssue();
		Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> performanceissueSet = new HashSet<>(performanceissue);
		unionSet.retainAll(performanceissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if (intersectionList.isEmpty())
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else
			return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}

	@GetMapping("/highprioritytickets")
	public ResponseEntity<List<Ticket>> getPerformanceHighPriorityTickets() throws TicketNotFoundException {
		// Retrieve lists from TicketService
		List<Ticket> mpvs = ticketservice.findTicketsWithHighPriorityAndVariousSeverities();
		List<Ticket> performanceissue = ticketservice.performanceIssue();
		Set<Ticket> unionSet = new HashSet<>();
		unionSet.addAll(mpvs);
		// Find intersection with connectivity issue
		Set<Ticket> performanceissueSet = new HashSet<>(performanceissue);
		unionSet.retainAll(performanceissueSet);

		// Convert set back to list
		List<Ticket> intersectionList = unionSet.stream().collect(Collectors.toList());
		// Return response with intersectionList
		if (intersectionList.isEmpty())
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		else
			return new ResponseEntity<>(intersectionList, HttpStatus.OK);
	}
	
	@GetMapping("/getcriticalticket")
	public ResponseEntity<List<Ticket>> getCriticalTicket() throws TicketNotFoundException {
		List<Ticket> performanceissue = ticketservice.performanceIssue();
		List<Ticket> tickets= ticketservice.getFourtyEightPlusAgedTickets();
		if(!tickets.isEmpty())
		{
		List<Ticket> intersection = performanceissue.stream()
		        .filter(tickets::contains) // Filter tickets in performanceissue that are also in tickets
		        .collect(Collectors.toList());
		List<Ticket> openTicket= ticketservice.getOpenTickets();
		List<Ticket> intersectionWithOpen = intersection.stream()
		        .filter(openTicket::contains) // Filter tickets in intersection that are also in openTicket
		        .collect(Collectors.toList());
		if(!intersectionWithOpen.isEmpty())
		{
		return new ResponseEntity<>(intersectionWithOpen, HttpStatus.OK);
		}
		else throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
		else throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}
}
