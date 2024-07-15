package com.prodapt.networkticketingapplicationproject.service;

import java.util.List;

import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.model.PriorityAnalysisReport;
import com.prodapt.networkticketingapplicationproject.model.ResolutionTimeReport;
import com.prodapt.networkticketingapplicationproject.model.TicketAgingReport;
import com.prodapt.networkticketingapplicationproject.requestentities.AdminUpdateCustomerTier;

public interface TicketService {

	public Ticket addTicket(Ticket ticket);

	public Ticket getTicketById(Integer ticketId) throws TicketNotFoundException;

	public Ticket updateTicket(Ticket ticket) throws TicketNotFoundException;

	public List<Ticket> getByPriority(Priority priority) throws TicketNotFoundException;

	public List<Ticket> getBySeverity(Severity severity) throws TicketNotFoundException;

	public List<Ticket> getByUserEntity(UserEntity userEntity) throws TicketNotFoundException;

	public List<Ticket> getAllTickets() throws TicketNotFoundException;

	public List<Ticket> getFourtyEightPlusAgedTickets() throws TicketNotFoundException;

	public List<Ticket> getTwentyFourPlusAgedTickets() throws TicketNotFoundException;

	public List<Ticket> getOpenTickets() throws TicketNotFoundException;


	//issue handles

	public List<Ticket> hardwareIssue() throws TicketNotFoundException;

	public List<Ticket> connectivityIssue() throws TicketNotFoundException;

	public List<Ticket> performanceIssue() throws TicketNotFoundException;

	public List<Ticket> administrativeIssue() throws TicketNotFoundException;
	

	
	

	//
	public List<Ticket> findTicketsWithHighPriorityAndVariousSeverities() throws TicketNotFoundException;
	public List<Ticket> findTicketsWithMediumPriorityAndVariousSeverities() throws TicketNotFoundException;
	public List<Ticket> findTicketsWithLowPriorityAndVariousSeverities() throws TicketNotFoundException;
	
	public List<TicketAgingReport> getTicketAgingReport() throws TicketNotFoundException;
	 
	public List<ResolutionTimeReport> getResolutionTimeReport() throws TicketNotFoundException;
 
	public List<PriorityAnalysisReport> getPriorityAnalysisReport()throws TicketNotFoundException;
 
	
	//
	
	
	
}
