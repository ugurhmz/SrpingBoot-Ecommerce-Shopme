package com.ugurhmz.admin.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ugurhmz.admin.security.SecurityUserDetails;
import com.ugurhmz.admin.user.repositories.UserRepository;
import com.ugurhmz.common.entity.User;



public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.getByUserEmail(email);
		
		if(user != null) {
			return new SecurityUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email : "+email);
		
	}

}









