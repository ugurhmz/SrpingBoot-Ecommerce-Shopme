package com.ugurhmz.admin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ugurhmz.admin.user.services.UserService;
import com.ugurhmz.common.entity.User;




@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	// GET ALL USER LIST
	@GetMapping("/users")
	public String listAllUsers(Model model) {
		List<User> listUsers =   userService.listAllusers();
		model.addAttribute("allUsers",listUsers);
	
		return "users";
	}
	
}
