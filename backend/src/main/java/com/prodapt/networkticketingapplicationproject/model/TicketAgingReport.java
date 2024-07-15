package com.prodapt.networkticketingapplicationproject.model;

import java.time.LocalDate;
import com.prodapt.networkticketingapplicationproject.entities.Priority;
import com.prodapt.networkticketingapplicationproject.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketAgingReport {
	private Integer ticket_id;
	private String user_name;
	private String title;
	private LocalDate creation_date;
	private Status status;
	private Priority priority;
	private LocalDate last_updated;
	private Long aging_days;
}

