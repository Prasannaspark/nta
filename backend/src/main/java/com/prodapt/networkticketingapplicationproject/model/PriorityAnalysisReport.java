package com.prodapt.networkticketingapplicationproject.model;

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
public class PriorityAnalysisReport {
	private String priority;
	private Long count;
}