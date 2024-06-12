package com.prodapt.networkticketingapplicationproject.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prodapt.networkticketingapplicationproject.entities.Role;
import com.prodapt.networkticketingapplicationproject.entities.Ticket;
import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
import com.prodapt.networkticketingapplicationproject.exceptions.TicketNotFoundException;
import com.prodapt.networkticketingapplicationproject.requestentities.UpdateUserRoleRequest;
import com.prodapt.networkticketingapplicationproject.service.RoleService;
import com.prodapt.networkticketingapplicationproject.service.TicketService;
import com.prodapt.networkticketingapplicationproject.service.UserEntityService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins="*", maxAge=3600)
public class AdminController {
	private static final Logger loggers = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserEntityService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TicketService ticketService;

	@GetMapping("/getalltickets")
	public ResponseEntity<List<Ticket>> getAll() throws TicketNotFoundException {
		List<Ticket> tickets= ticketService.getAllTickets();
		loggers.info("getall");
		return new ResponseEntity<List<Ticket>>(tickets,HttpStatus.OK);
	}
	
	@GetMapping("/getallusers")
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		List<UserEntity> users= userService.getAll();
		return new ResponseEntity<List<UserEntity>>(users,HttpStatus.OK);
		
	}
	
	@PostMapping("/updateuserrole")
	public ResponseEntity<String> updateUserRole(@RequestBody UpdateUserRoleRequest updateUserRoleRequest) throws RoleNotFoundException {
		
		// Find the role by name
		Optional<Role> r = roleService.findRoleByName(updateUserRoleRequest.getRole());
		
		// Update the user role
		String message = userService.updaterole(updateUserRoleRequest.getId(), r.get());
		loggers.info("Role Updated Successfully");
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
}