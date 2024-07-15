package com.prodapt.networkticketingapplicationproject.requestentities;

import com.prodapt.networkticketingapplicationproject.entities.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketUpdateIT {
	
	private Integer ticketId;
	
	@Enumerated(EnumType.STRING)
	private Status status;
}
