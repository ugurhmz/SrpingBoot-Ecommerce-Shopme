package com.ugurhmz.admin.user.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncoderTest {

	
	
	@Test
	public void testPasswordEncode() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String myPassword = "ugur67";
		String encodedPassword = passwordEncoder.encode(myPassword);
		
		System.out.println(encodedPassword);
		
		boolean pwMatches = passwordEncoder.matches(myPassword, encodedPassword);
		
		assertThat(pwMatches).isTrue();
	}
}
