package com.prodapt.networkticketingapplicationproject.requestentities;




import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class TicketRequest {
		
	private String title;
	
	private String description;
	
	private String username;

}
