package com.ugurhmz.admin.user.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ugurhmz.common.entity.Role;
import com.ugurhmz.common.entity.User;



@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	// create user one role
	@Test
	public void testCreateNewUserWithOneRole() {
		Role adminRole =entityManager.find(Role.class, 1);
		
		
		User userUgurhmz = new User("Ugur","Hamzaoglu","craxx67@gmail.com","123123");
		userUgurhmz.addRole(adminRole);
		
		User userSaved = userRepository.save(userUgurhmz);
		assertThat(userSaved.getId()).isGreaterThan(0);
	}
	
	// create user two roles
	@Test
	public void testCreateMultipleRoles() {
		User userAyse = new User("Ayse","Uçak","ayse@gmail.com","67625251");
		
		Role editorRole = new Role(3);
		Role assistantRole = new Role(5);
		
		userAyse.addRole(editorRole);
		userAyse.addRole(assistantRole);
		
		
		User userSaved = userRepository.save(userAyse);
		assertThat(userSaved.getId()).isGreaterThan(0);
	}
	
	
	// list all users
	@Test
	public void testListAllUsers() {
		Iterable<User> users = userRepository.findAll();
		users.forEach(user -> System.out.println(user));
	}
	
	
	// geet user findById
	@Test
	public void testUserFindId() {
		User userNamHa = userRepository.findById(1).get(); 
		System.out.println(userNamHa);
		
		assertThat(userNamHa.getId()).isNotNull();
	}
	
	
	// Email test
	@Test
	public void testUserByEmail() {
		String email = "craxx67@gmail.com";
		
		assertThat(userRepository.getByUserEmail(email)).isNotNull();
	}
	
	
	
	// delete user test
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		userRepository.deleteById(userId);
	}
	
	
	
	// update user  Test
	@Test
	public void testUpdateUser() {
		User namH = userRepository.findById(1).get();
		namH.setEnabled(true);
		namH.setFirstName("Nam H ");
		
		userRepository.save(namH);
	}
	
	
	
	
	// User enabled/disabled status test
	@Test
	public void testStatus() {
		
		Integer id = 1;
		//userRepository.updateEnableStatus(id, false);
		userRepository.updateEnableStatus(id, false);
	}
	
	
}


























