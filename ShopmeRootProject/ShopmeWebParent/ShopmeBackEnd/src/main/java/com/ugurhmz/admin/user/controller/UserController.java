package com.ugurhmz.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.admin.FileUploadUtil;
import com.ugurhmz.admin.user.services.UserService;
import com.ugurhmz.common.entity.Role;
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
	
	
	// GET NEW USER
	@GetMapping("/users/new")
	public String getNewUser(Model model) {
		List<Role> listRoles = userService.listAllRoles();
		User user = new User();
		
		user.setEnabled(true);
		
		model.addAttribute("user",user);
		model.addAttribute("roles",listRoles);
		model.addAttribute("pageTitle","Create New User");
		
		
		return "newUserForm";
	}
	
	// POST NEW USER
	@PostMapping("/users/save")		//It'll be same in action  th:action="@{/users/save}
	public String postNewUser(
			User user, RedirectAttributes redirectAttributes,
			@RequestParam("imageInput") MultipartFile multipartFile ) throws IOException 
	{
		
		if(!multipartFile.isEmpty()) {
			
			String fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			
			User savedUser = userService.save(user);
			
			String uploadDir = "user-photos/" +savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);	//Clean old photos
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			
			if(user.getPhotos().isEmpty())  user.setPhotos(null);
			
			userService.save(user);
		}
		
		//Message when redirect after
		redirectAttributes.addFlashAttribute("message", "User has been saved  successfully");
		
		return "redirect:/users";
	}
	
	
	
}















