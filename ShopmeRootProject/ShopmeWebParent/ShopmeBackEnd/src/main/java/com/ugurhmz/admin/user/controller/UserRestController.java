package com.ugurhmz.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ugurhmz.admin.user.services.UserService;




@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/users/check-email-user")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		
		if(userService.isEmailUnique(id,email)) {
			return "User successfully added.";
		}
		
		return "This email is already USE !!";
	}
	
}
