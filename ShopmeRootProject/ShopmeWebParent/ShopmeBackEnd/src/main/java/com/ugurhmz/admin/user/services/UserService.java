package com.ugurhmz.admin.user.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ugurhmz.admin.user.repositories.RoleRepository;
import com.ugurhmz.admin.user.repositories.UserRepository;
import com.ugurhmz.common.entity.Role;
import com.ugurhmz.common.entity.User;



@Service
@Transactional
public class UserService {
	
	public static final int USER_PER_PAGE = 4;
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	//LIST ALL USERS
	public List<User> listAllusers(){
		return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());	
	}
	
	
	// LIST ROLES
	public List<Role> listAllRoles() {
		
		return (List<Role>) roleRepository.findAll();
	}
	
	
	
	
	/* BEFORE SORTING -> PAGINATON
	 * 
	public Page<User> listByPage(int pageNum){
		
		//paginate
		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);
		return userRepository.findAll(pageable);
	} */
	
	
	
	
	//  PAGINATION
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		
		// for sort
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		
		// for paginate
		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort);
		
		
		
		//search
		if(keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}
	
		return userRepository.findAll(pageable);
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

	
	

	//Delete User
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user ID : "+id);
		}
		
		userRepository.deleteById(id);
		
	}
	
	
	
	
	//User status update
	public void userStatusUpdate(Integer id, boolean enabled) {
		userRepository.updateEnableStatus(id, enabled);
	}
	
	
	
	
	// Get user Id
	public User getUserWithId(Integer id) throws UserNotFoundException {
		
		try {
			return userRepository.findById(id).get();
			
		} catch(NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any user with ID : "+id);
		}
		
	}
	
	
}












