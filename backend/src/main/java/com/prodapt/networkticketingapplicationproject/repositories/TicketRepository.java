package com.prodapt.networkticketingapplicationproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;
import com.prodapt.networkticketingapplicationproject.entities.IssueType;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Severity;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;

@Repository
public interface TicketRepository extends CrudRepository<Ticket,Integer>
{
	
	public List<Ticket> findByPriority(Priority priority);
	
	public List<Ticket> findBySeverity(Severity severity);
	
	public List<Ticket> findByUser(UserEntity userEntity);
	
	public List<Ticket> findByCustomerTier(CustomerTier customerTier);

	public List<Ticket> findAll();
	
	public List<Ticket> findByStatus(Status status);
	
	public List<Ticket> findByIssueType(IssueType issueType);
	
	public List<Ticket> findByUserId(Integer userId);

	@Query(value = "SELECT t.ticket_id, u.username, t.title, t.creation_date, t.status, t.priority, t.last_updated, DATEDIFF(CURDATE(), t.creation_date) as agingDays FROM tickets_table t join user_table u on t.user_id=u.id", nativeQuery = true)
    List<Object[]> generateTicketAgingReport();
 
    @Query(value = "SELECT t.ticket_id, u.username, t.title, t.creation_date, t.status, t.priority, t.last_updated, TIMESTAMPDIFF(DAY, t.creation_Date, t.last_updated) as resolutionTime FROM tickets_table t JOIN user_table u ON t.user_id = u.id WHERE t.status = 'CLOSE'", nativeQuery = true)
    List<Object[]> generateResolutionTimeReport();
 
    @Query(value = "SELECT t.priority, COUNT(*) FROM tickets_table t GROUP BY t.priority", nativeQuery = true)
    List<Object[]> generatePriorityAnalysisReport();

}
