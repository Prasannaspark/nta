package com.prodapt.networkticketingapplicationproject.requestentities;

import com.prodapt.networkticketingapplicationproject.entities.Request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateRequestforTier {
	
   private String username;
	
   @Enumerated(EnumType.STRING)
	private Request request;
  
   
   

}
