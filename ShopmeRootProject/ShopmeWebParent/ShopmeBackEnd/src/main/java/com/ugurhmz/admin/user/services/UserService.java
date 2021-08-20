package com.ugurhmz.admin.user.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ugurhmz.admin.user.repositories.RoleRepository;
import com.ugurhmz.admin.user.repositories.UserRepository;
import com.ugurhmz.common.entity.Role;
import com.ugurhmz.common.entity.User;



@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	//LIST ALL USERS
	public List<User> listAllusers(){
		return (List<User>) userRepository.findAll();	
	}
	
	
	// LIST ROLES
	public List<Role> listAllRoles() {
		
		return (List<Role>) roleRepository.findAll();
	}
	
	
	// CREATE & UPDATE
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if(isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
				
			} else {
				encodePassword(user);
			}
			
		}
		else {
			encodePassword(user);
		}
		
		return userRepository.save(user);
	}
	

	//password Encode
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
	}
	
	
	
	
	//Email is Unique ?
	public boolean isEmailUnique(Integer id, String email) {
		User userEmail = userRepository.getByUserEmail(email);
		
		if(userEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(userEmail != null) return false;
			
		} else {
			if(userEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	
	
}












