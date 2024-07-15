package com.prodapt.networkticketingapplicationproject.requestentities;


import com.prodapt.networkticketingapplicationproject.entities.ERole;

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
public class UpdateUserRoleRequest {
	private String username;
	private ERole role;
	
}

