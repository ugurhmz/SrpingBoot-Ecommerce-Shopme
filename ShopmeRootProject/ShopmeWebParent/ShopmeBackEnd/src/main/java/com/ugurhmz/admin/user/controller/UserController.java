package com.ugurhmz.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.admin.FileUploadUtil;
import com.ugurhmz.admin.user.services.UserNotFoundException;
import com.ugurhmz.admin.user.services.UserService;
import com.ugurhmz.common.entity.Role;
import com.ugurhmz.common.entity.User;




@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/*
	// GET ALL USER LIST  - BEFORE PAGINATION
	 
	@GetMapping("/users")
	public String listAllUsers(Model model) {
		List<User> listUsers =   userService.listAllusers();
		model.addAttribute("allUsers",listUsers);
	
		return "users";
	}	*/
	
	
	
	// AFTER PAGINATION LIST USERS
	@GetMapping("/users")
	public String getFirstPage(Model model) {
		
		return listByPage(1, model);
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
	
	
	
	
	// DELETE USER
	@GetMapping("/users/delete/{id}")
	public String userDelete(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message","The USER ID : "+id+" has been deleted successfully.");
			
			
		} catch(UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage());
		}
		
		return "redirect:/users";
	}
	
	
	
	
	// USER STATUS UPDATE
	@GetMapping("/users/{id}/enabled/{status}")
	public String userStatusUpdate(
			@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes
			)
	{
		userService.userStatusUpdate(id, enabled);
		
		String statusInfo  = enabled ? "enabled" : "disabled";
		String message = "The USER ID : "+id+ " has ben "+statusInfo;
		
		redirectAttributes.addFlashAttribute("message",message);
		
		return "redirect:/users";
	}
	
	
	
	
	// GET UPDATE USER
	@GetMapping("/users/edit/{id}")
	public String getUpdateUser(
			@PathVariable("id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) 
	
	{
		
		try {
			User user = userService.getUserWithId(id);
			List<Role> listRoles = userService.listAllRoles();
			
			model.addAttribute("user",user);
			model.addAttribute("pageTitle","Update User : "+id);
			model.addAttribute("roles",listRoles);
			
			return "newUserForm";
			
		} catch(UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage());
			return "redirect:/users";
		}
		
	}
	
	
	// PAGINATION
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(
			@PathVariable(name="pageNum") int pageNum,Model model) 
	{
		Page<User> page = userService.listByPage(pageNum);
		List<User> listUsers = page.getContent();
		
		long startCount = (pageNum - 1) * UserService.USER_PER_PAGE + 1;
		long endCount = startCount  + UserService.USER_PER_PAGE -1 ;
		
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("allUsers", listUsers);
		
		return "users";
		
	}
	
	
	
	
	
	
	
	
	
	
	
}















