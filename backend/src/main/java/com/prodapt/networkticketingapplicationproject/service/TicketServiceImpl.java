package com.prodapt.networkticketingapplicationproject.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.prodapt.networkticketingapplicationproject.requestentities.AdminUpdateCustomerTier;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository repo;
	
	
	@Autowired
	UserRepository userrepo;

	
	@Override
	public Ticket addTicket(Ticket ticket) {
		
		Optional<UserEntity>userdata=userrepo.findById(ticket.getUser().getId());
		ticket.setCustomerTier(userdata.get().getTier());
		ticket.setStatus(Status.OPEN);
		ticket.setCreationDate(LocalDate.now());
		ticket.setLastUpdated(LocalDate.now());
		return repo.save(ticket);
	}

	@Override
	public Ticket getTicketById(Integer ticketId) throws TicketNotFoundException {
		Optional<Ticket> t = repo.findById(ticketId);
		if (t.isPresent()) {
			return t.get();
		} else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public Ticket updateTicket(Ticket ticket) throws TicketNotFoundException {
		Optional<Ticket> t = repo.findById(ticket.getTicketId());
		if (t.isPresent()) {
			return repo.save(ticket);
		} else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getByPriority(Priority priority) throws TicketNotFoundException {
		List<Ticket> ticketList = repo.findByPriority(priority);
		if (!ticketList.isEmpty()) {
			return ticketList;
		} else
		//	return null;
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getBySeverity(Severity severity) throws TicketNotFoundException {
		List<Ticket> ticketList = repo.findBySeverity(severity);
		if (!ticketList.isEmpty())
			return ticketList;
		else
			//return null;
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getByUserEntity(UserEntity userEntity) throws TicketNotFoundException {
		List<Ticket> ticketList = repo.findByUser(userEntity);
		if (!ticketList.isEmpty())
			return ticketList;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getAllTickets() throws TicketNotFoundException {
		List<Ticket> ticketList = repo.findAll();
		if (!ticketList.isEmpty())
			return ticketList;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getFourtyEightPlusAgedTickets() throws TicketNotFoundException {
		LocalDate currentDate = LocalDate.now();
		LocalDate fortyEightHoursAgo = currentDate.minusDays(2);

		List<Ticket> allTickets = repo.findAll();
		
		List<Ticket> agedTickets = allTickets.stream()
				.filter(ticket -> ticket.getCreationDate().isBefore(fortyEightHoursAgo)).collect(Collectors.toList());
		
		if (!agedTickets.isEmpty())
			return agedTickets;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getTwentyFourPlusAgedTickets() throws TicketNotFoundException {
		LocalDate currentDate = LocalDate.now();
		LocalDate twentyFourHoursAgo = currentDate.minusDays(1);

	
		List<Ticket> allTickets = getAllTickets();

		// Filter tickets older than forty-eight hours
		List<Ticket> agedTickets = allTickets.stream()
				.filter(ticket -> ticket.getCreationDate().isBefore(twentyFourHoursAgo)).collect(Collectors.toList());

		if (!agedTickets.isEmpty())
			return agedTickets;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getOpenTickets() throws TicketNotFoundException {
		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);
		if (!openTickets.isEmpty())
			return openTickets;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> hardwareIssue() throws TicketNotFoundException {

		List<Ticket> hardwareissue = repo.findByIssueType(IssueType.HARDWARE);
		
		if (!hardwareissue.isEmpty()) {
			return hardwareissue;
		} else {
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
	}

	@Override
	public List<Ticket> connectivityIssue() throws TicketNotFoundException {
		List<Ticket> connectivityissue = repo.findByIssueType(IssueType.CONNECTIVITY);
		if (!connectivityissue.isEmpty()) {
			return connectivityissue;
		} else {
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
	}

	@Override
	public List<Ticket> performanceIssue() throws TicketNotFoundException {
		List<Ticket> performaceissue = repo.findByIssueType(IssueType.PERFORMANCE);
		if (!performaceissue.isEmpty()) {
			return performaceissue;
		} else {
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
	}

	@Override
	public List<Ticket> administrativeIssue() throws TicketNotFoundException {
		List<Ticket> adminissue = repo.findByIssueType(IssueType.ADMINISTRATIVE);
		if (!adminissue.isEmpty()) {
			return adminissue;
		} else {
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
		}
	}


	
	@Override
	public List<Ticket> findTicketsWithHighPriorityAndVariousSeverities() throws TicketNotFoundException {
	    List<Ticket> highpriority = repo.findByPriority(Priority.HIGH);
	    List<Ticket> opentickets = repo.findByStatus(Status.OPEN);

	    Set<Ticket> highprioritySet = new HashSet<>(highpriority);
	    Set<Ticket> openticketsSet = new HashSet<>(opentickets);

	    // Initialize lists for different severities
	    List<Ticket> highSeverityTickets = new ArrayList<>();
	    List<Ticket> mediumSeverityTickets = new ArrayList<>();
	    List<Ticket> lowSeverityTickets = new ArrayList<>();

	    // Retrieve tickets for each severity level and categorize them
	    List<Ticket> highSeverity = repo.findBySeverity(Severity.HIGH);
	    List<Ticket> mediumSeverity = repo.findBySeverity(Severity.MEDIUM);
	    List<Ticket> lowSeverity = repo.findBySeverity(Severity.LOW);

	    highSeverityTickets.addAll(highSeverity);
	    mediumSeverityTickets.addAll(mediumSeverity);
	    lowSeverityTickets.addAll(lowSeverity);

	    // Find intersection with high priority and open tickets
	    highSeverityTickets.retainAll(highprioritySet);
	    mediumSeverityTickets.retainAll(highprioritySet);
	    lowSeverityTickets.retainAll(highprioritySet);

	    highSeverityTickets.retainAll(openticketsSet);
	    mediumSeverityTickets.retainAll(openticketsSet);
	    lowSeverityTickets.retainAll(openticketsSet);

	    // Concatenate lists in the desired order
	    List<Ticket> result = new ArrayList<>();
	    result.addAll(highSeverityTickets);
	    result.addAll(mediumSeverityTickets);
    result.addAll(lowSeverityTickets);

	    if (!result.isEmpty()) {
	        return result;
	    } else {
	        throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	    }
	}

	@Override
	public List<Ticket> findTicketsWithMediumPriorityAndVariousSeverities() throws TicketNotFoundException {
	    List<Ticket> highpriority = repo.findByPriority(Priority.MEDIUM);
	    List<Ticket> opentickets = repo.findByStatus(Status.OPEN);

	    Set<Ticket> highprioritySet = new HashSet<>(highpriority);
	    Set<Ticket> openticketsSet = new HashSet<>(opentickets);

	    // Initialize lists for different severities
	    List<Ticket> highSeverityTickets = new ArrayList<>();
	    List<Ticket> mediumSeverityTickets = new ArrayList<>();
	    List<Ticket> lowSeverityTickets = new ArrayList<>();

	    // Retrieve tickets for each severity level and categorize them
	    List<Ticket> highSeverity = repo.findBySeverity(Severity.HIGH);
	    List<Ticket> mediumSeverity = repo.findBySeverity(Severity.MEDIUM);
	    List<Ticket> lowSeverity = repo.findBySeverity(Severity.LOW);

	    highSeverityTickets.addAll(highSeverity);
	    mediumSeverityTickets.addAll(mediumSeverity);
	    lowSeverityTickets.addAll(lowSeverity);

	    // Find intersection with high priority and open tickets
	    highSeverityTickets.retainAll(highprioritySet);
	    mediumSeverityTickets.retainAll(highprioritySet);
	    lowSeverityTickets.retainAll(highprioritySet);

	    highSeverityTickets.retainAll(openticketsSet);
	    mediumSeverityTickets.retainAll(openticketsSet);
	    lowSeverityTickets.retainAll(openticketsSet);

	    // Concatenate lists in the desired order
	    List<Ticket> result = new ArrayList<>();
	    result.addAll(highSeverityTickets);
	    result.addAll(mediumSeverityTickets);
	    result.addAll(lowSeverityTickets);

	    if (!result.isEmpty()) {
	        return result;
	    } else {
	        throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	    }
	}


	@Override
	public List<Ticket> findTicketsWithLowPriorityAndVariousSeverities() throws TicketNotFoundException {
	    List<Ticket> highpriority = repo.findByPriority(Priority.LOW);
	    List<Ticket> opentickets = repo.findByStatus(Status.OPEN);

	    Set<Ticket> highprioritySet = new HashSet<>(highpriority);
	    Set<Ticket> openticketsSet = new HashSet<>(opentickets);

	    // Initialize lists for different severities
	    List<Ticket> highSeverityTickets = new ArrayList<>();
	    List<Ticket> mediumSeverityTickets = new ArrayList<>();
	    List<Ticket> lowSeverityTickets = new ArrayList<>();

	    // Retrieve tickets for each severity level and categorize them
	    List<Ticket> highSeverity = repo.findBySeverity(Severity.HIGH);
	    List<Ticket> mediumSeverity = repo.findBySeverity(Severity.MEDIUM);
	    List<Ticket> lowSeverity = repo.findBySeverity(Severity.LOW);

	    highSeverityTickets.addAll(highSeverity);
	    mediumSeverityTickets.addAll(mediumSeverity);
	    lowSeverityTickets.addAll(lowSeverity);

	    // Find intersection with high priority and open tickets
	    highSeverityTickets.retainAll(highprioritySet);
	    mediumSeverityTickets.retainAll(highprioritySet);
	    lowSeverityTickets.retainAll(highprioritySet);

	    highSeverityTickets.retainAll(openticketsSet);
	    mediumSeverityTickets.retainAll(openticketsSet);
	    lowSeverityTickets.retainAll(openticketsSet);

	    // Concatenate lists in the desired order
	    List<Ticket> result = new ArrayList<>();
	    result.addAll(highSeverityTickets);
	    result.addAll(mediumSeverityTickets);
	    result.addAll(lowSeverityTickets);

	    if (!result.isEmpty()) {
	        return result;
	    } else {
	        throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	    }
	}
	
	@Override
	public List<PriorityAnalysisReport> getPriorityAnalysisReport() throws TicketNotFoundException{
		List<Object[]> results = repo.generatePriorityAnalysisReport();
		List<PriorityAnalysisReport> reports = new ArrayList<>();
		for (Object[] result:results) {
			PriorityAnalysisReport report = new PriorityAnalysisReport();
			report.setPriority(Enum.valueOf(Priority.class,(String)result[0]).toString());
			report.setCount((Long)result[1]);
			reports.add(report);
		}
		return reports;
	}

	@Override
	public List<TicketAgingReport> getTicketAgingReport() throws TicketNotFoundException {
	    List<Object[]> results = repo.generateTicketAgingReport();
	    if (results.isEmpty()) {
	        throw new TicketNotFoundException("No tickets found");
	    }
	    List<TicketAgingReport> reports = new ArrayList<>();
	    for (Object[] result : results) {
	        TicketAgingReport report = new TicketAgingReport();
	        report.setTicket_id((Integer) result[0]);
	        report.setUser_name((String) result[1]);
	        report.setTitle((String) result[2]);
	        Date sqlDate = (Date) result[3];
	        report.setCreation_date(sqlDate.toLocalDate());
	        report.setStatus(Enum.valueOf(Status.class, (String) result[4]));
	        report.setPriority(Enum.valueOf(Priority.class, (String) result[5]));
	        Date sqlDate1 = (Date) result[6];
	        report.setLast_updated(sqlDate1.toLocalDate());
	        report.setAging_days((Long) result[7]);
	        reports.add(report);
	    }
	    return reports;
	}
 
 
	@Override
	public List<ResolutionTimeReport> getResolutionTimeReport() throws TicketNotFoundException{
	    List<Object[]> results = repo.generateResolutionTimeReport();
	    if (results.isEmpty()) {
	        return Collections.emptyList();
	    }
	    List<ResolutionTimeReport> reports = new ArrayList<>();
	    for (Object[] result : results) {
	        ResolutionTimeReport report = new ResolutionTimeReport();
	        report.setTicketId((Integer) result[0]);
	        report.setUsername((String) result[1]);
	        report.setTitle((String) result[2]);
	        Date sqlDate = (Date) result[3];
	        report.setCreationDate(sqlDate.toLocalDate());
	        report.setStatus(Enum.valueOf(Status.class, (String) result[4]));
	        report.setPriority(Enum.valueOf(Priority.class, (String) result[5]));
	        Date sqlDate1 = (java.sql.Date) result[6];
	        report.setLastUpdated(sqlDate1.toLocalDate());
	        report.setResolutiontime((Long) result[7]);
	        reports.add(report);
	    }
	    return reports;
	}


	}
	


