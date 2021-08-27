package com.ugurhmz.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.admin.FileUploadUtil;
import com.ugurhmz.admin.security.SecurityUserDetails;
import com.ugurhmz.admin.user.services.UserService;
import com.ugurhmz.common.entity.User;



@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	// GET ACCOUNT PAGE
	@GetMapping("/account")
	public String getAccountDetails(
			@AuthenticationPrincipal SecurityUserDetails loggedUser, 
			Model model) 
	{
		String email = loggedUser.getUsername();
		User user = userService.getByEmail(email);
		
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Account Details Form");
		
		return "users/accountForm";
	}
	
	
	
	
		// POST ACCOUNT UPDATE
		@PostMapping("/account/update")		//It'll be same in action  th:action="@{/account/update}
		public String postNewUser(
				User user, RedirectAttributes redirectAttributes,
				@AuthenticationPrincipal SecurityUserDetails loggedUser,
				@RequestParam("imageInput") MultipartFile multipartFile ) throws IOException 
		{
			
			if(!multipartFile.isEmpty()) {
				
				String fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
				user.setPhotos(fileName);
				
				User savedUser = userService.accountUpdate(user);
				
				String uploadDir = "user-photos/" +savedUser.getId();
				FileUploadUtil.cleanDir(uploadDir);	//Clean old photos
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				
			} else {
				
				if(user.getPhotos().isEmpty())  user.setPhotos(null);
				userService.accountUpdate(user);
			}
			
			
			loggedUser.setFirstName(user.getFirstName());
			loggedUser.setLastName(user.getLastName());
			
			
			//Message when redirect after
			redirectAttributes.addFlashAttribute("message", "Account has been updated.");
			
			
			return "redirect:/account";
			
		}
	
	
	
	
}
