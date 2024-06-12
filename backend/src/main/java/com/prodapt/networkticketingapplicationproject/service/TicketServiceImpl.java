package com.prodapt.networkticketingapplicationproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptionmessages.QueryMapper;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.repositories.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository repo;

	@Override
	public Ticket addTicket(Ticket ticket) {
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
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getBySeverity(Severity severity) throws TicketNotFoundException {
		List<Ticket> ticketList = repo.findBySeverity(severity);
		if (!ticketList.isEmpty())
			return ticketList;
		else
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
	public List<Ticket> getRoutineTickets() throws TicketNotFoundException {
		List<Ticket> mediumPriority = repo.findByPriority(Priority.MEDIUM);

		List<Ticket> goldTicket = repo.findByCustomerTier(CustomerTier.GOLD);
		List<Ticket> lowPriority = repo.findByPriority(Priority.LOW);
		List<Ticket> intersection = new ArrayList<>();
		for (Ticket ticket : goldTicket) {
			if (lowPriority.contains(ticket)) {
				intersection.add(ticket);
			}
		}

		List<Ticket> union = new ArrayList<>(intersection);
		for (Ticket ticket : mediumPriority) {
			if (!union.contains(ticket)) {
				union.add(ticket);
			}
		}

		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);
		List<Ticket> intersectionOfUnionAndOpen = new ArrayList<>();
		for (Ticket ticket : union) {
			if (openTickets.contains(ticket)) {
				intersectionOfUnionAndOpen.add(ticket);
			}
		}
		List<Ticket> twentyFourHoursPlus = getTwentyFourPlusAgedTickets();
		List<Ticket> notInTwentyFourHoursPlus = new ArrayList<>();
		for (Ticket ticket : intersectionOfUnionAndOpen) {
			if (!twentyFourHoursPlus.contains(ticket)) {
				notInTwentyFourHoursPlus.add(ticket);
			}
		}
		if (!notInTwentyFourHoursPlus.isEmpty())
			return notInTwentyFourHoursPlus;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getMinorTickets() throws TicketNotFoundException {

		List<Ticket> lowPriority = repo.findByPriority(Priority.LOW);
		List<Ticket> lowSeverity = repo.findBySeverity(Severity.LOW);
		List<Ticket> intersection = lowPriority.stream().filter(lowSeverity::contains).collect(Collectors.toList());
		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);
		List<Ticket> intersectionOfIntersectionAndOpen = intersection.stream().filter(openTickets::contains)
				.collect(Collectors.toList());

		List<Ticket> twentyFourHoursPlus = getTwentyFourPlusAgedTickets();
		List<Ticket> notInTwentyFourHoursPlus = new ArrayList<>();
		for (Ticket ticket : intersectionOfIntersectionAndOpen) {
			if (!twentyFourHoursPlus.contains(ticket)) {
				notInTwentyFourHoursPlus.add(ticket);
			}
		}
		if (!notInTwentyFourHoursPlus.isEmpty())
			return notInTwentyFourHoursPlus;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getCriticalTickets() throws TicketNotFoundException {
		List<Ticket> highPriority = repo.findByPriority(Priority.HIGH);
		List<Ticket> highSeverity = repo.findBySeverity(Severity.HIGH);
		List<Ticket> highPrioritySeverity = new ArrayList<>();
		for (Ticket ticket : highPriority) {
			if (highSeverity.contains(ticket)) {
				highPrioritySeverity.add(ticket);
			}
		}

		List<Ticket> goldTicket = repo.findByCustomerTier(CustomerTier.GOLD);
		List<Ticket> mediumPriority = repo.findByPriority(Priority.MEDIUM);
		List<Ticket> goldMediumTicket = new ArrayList<>();
		for (Ticket ticket : goldTicket) {
			if (mediumPriority.contains(ticket)) {
				goldMediumTicket.add(ticket);
			}
		}

		List<Ticket> union = new ArrayList<>(goldMediumTicket);
		union.addAll(highPrioritySeverity);

		List<Ticket> fourtyEightHoursPlus = getFourtyEightPlusAgedTickets();
		List<Ticket> finalUnion = new ArrayList<>(union);
		finalUnion.addAll(fourtyEightHoursPlus);

		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);
		List<Ticket> intersectionFinal = new ArrayList<>();
		
		for (Ticket ticket : finalUnion) {
			if (openTickets.contains(ticket)) {
				intersectionFinal.add(ticket);
			}
		}

		if (!intersectionFinal.isEmpty())
			return intersectionFinal;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);

	}

	@Override
	public List<Ticket> getUrgentButNotCriticalTickets() throws TicketNotFoundException {

		List<Ticket> highPriority = repo.findByPriority(Priority.HIGH);
		List<Ticket> mediumSeverity = repo.findBySeverity(Severity.MEDIUM);
		List<Ticket> highPrioritymediumSeverity = new ArrayList<>();
		for (Ticket ticket : highPriority) {
			if (mediumSeverity.contains(ticket)) {
				highPrioritymediumSeverity.add(ticket);
			}
		}

		List<Ticket> goldTicket = repo.findByCustomerTier(CustomerTier.GOLD);
		List<Ticket> mediumPriority = repo.findByPriority(Priority.MEDIUM);
		List<Ticket> goldMediumPriority = new ArrayList<>();
		for (Ticket ticket : goldTicket) {
			if (mediumPriority.contains(ticket)) {
				goldMediumPriority.add(ticket);
			}
		}

		List<Ticket> union = new ArrayList<>(highPrioritymediumSeverity);
		union.addAll(goldMediumPriority);

		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);

		List<Ticket> intersection = new ArrayList<>();
		for (Ticket ticket : union) {
			if (openTickets.contains(ticket)) {
				intersection.add(ticket);
			}
		}
		List<Ticket> twentyFourHoursPlus = getTwentyFourPlusAgedTickets();
		List<Ticket> notInTwentyFourHoursPlus = new ArrayList<>();
		for (Ticket ticket : intersection) {
			if (!twentyFourHoursPlus.contains(ticket)) {
				notInTwentyFourHoursPlus.add(ticket);
			}
		}
		if (!notInTwentyFourHoursPlus.isEmpty())
			return notInTwentyFourHoursPlus;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);
	}

	@Override
	public List<Ticket> getNotUrgentButCriticalTickets() throws TicketNotFoundException {
		List<Ticket> lowPriority = repo.findByPriority(Priority.LOW);
		List<Ticket> highSeverity = repo.findBySeverity(Severity.HIGH);

		List<Ticket> lowPriorityhighSeverity = new ArrayList<>();
		for (Ticket ticket : lowPriority) {
			if (highSeverity.contains(ticket)) {
				lowPriorityhighSeverity.add(ticket);
			}
		}
		List<Ticket> twentyFourHoursPlusTickets = getTwentyFourPlusAgedTickets();

		List<Ticket> union = new ArrayList<>(lowPriorityhighSeverity);
		union.addAll(twentyFourHoursPlusTickets);

		List<Ticket> openTickets = repo.findByStatus(Status.OPEN);

		List<Ticket> intersection = new ArrayList<>();
		for (Ticket ticket : union) {
			if (openTickets.contains(ticket)) {
				intersection.add(ticket);
			}
		}
		
		List<Ticket> notFourtyEightHoursPlusTickets = getFourtyEightPlusAgedTickets();
		List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : intersection) {
            if (!notFourtyEightHoursPlusTickets.contains(ticket)) {
                result.add(ticket);
            }
        }
		if (!result.isEmpty())
			return result;
		else
			throw new TicketNotFoundException(QueryMapper.TICKETNOTFOUND);

	}

	@Override
	public List<Ticket> getFourtyEightPlusAgedTickets() throws TicketNotFoundException {
		LocalDate currentDate = LocalDate.now();
		LocalDate fortyEightHoursAgo = currentDate.minusDays(2);

		// Assuming there's a method to retrieve all tickets
		List<Ticket> allTickets = repo.findAll();

		// Filter tickets older than forty-eight hours
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

		// Assuming there's a method to retrieve all tickets
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

}
