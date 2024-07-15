package com.prodapt.networkticketingapplicationproject.requestentities;

import com.prodapt.networkticketingapplicationproject.entities.CustomerTier;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUpdateCustomerTier {
  
	private String  username;
    
    @Enumerated(EnumType.STRING)
	private CustomerTier customertier;
	
}
