package com.sanvic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanvic.entity.User;
import com.sanvic.service.UserRegistrationServiceImpl;

@RestController
public class UserController {

	private UserRegistrationServiceImpl userService;
	
	@Autowired
	public UserController(UserRegistrationServiceImpl userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
	}
	@PostMapping(
					value = "/saveuser",
					produces = "text/plain",
					consumes = "application/json"
				)
	public String handleSaveUser(@RequestBody User user) {
		
		/*
		 * User user = new User(); user.setFirstName("Vishant");
		 * user.setLastName("Singh"); user.setAccountStatus("LOCKED");
		 * user.setCity_id(1); user.setCountry_id(1); user.setCreatedDate(new Date());
		 * user.setDob(new Date(1995,5,28)); user.setEmail("myselfvishant@gmail.com");
		 * user.setGender("Male"); user.setPhno(9410872710L); user.setState_id(1);
		 * user.setUpdatedDate(new Date());
		 */
		Boolean isSaved = userService.saveUserDetails(user);
		if(isSaved) {
			System.out.println("Record Saved..");
			return "Record Saved..";
			
		}
		else {
			System.out.println("Record not Saved.. !!");
			return "Record not Saved.. !!";
		}
	}
	
	@GetMapping("/check")
	public String check() {
		return "Connection Success..";
	}
}
