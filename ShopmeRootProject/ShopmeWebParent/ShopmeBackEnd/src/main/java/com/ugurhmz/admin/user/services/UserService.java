package com.ugurhmz.admin.user.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	//LIST ALL USERS
	public List<User> listAllusers(){
		return (List<User>) userRepository.findAll();	
	}
	
	
	// LIST ROLES
	public List<Role> listAllRoles() {
		
		return (List<Role>) roleRepository.findAll();
	}
	
	
	
}












