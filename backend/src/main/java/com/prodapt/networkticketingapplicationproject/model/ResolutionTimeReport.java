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
public class ResolutionTimeReport {
	private Integer ticketId;
	private String username;
	private String title;
	private LocalDate creationDate;
	private Status status;
	private Priority priority;
	private LocalDate lastUpdated;
	private Long resolutiontime;
}
