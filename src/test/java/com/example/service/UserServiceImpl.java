package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceImpl {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Test
	public void loadUserByUsernameSuccessTest() {
		UserDetails userDetails = userService.loadUserByUsername("user");
		assertEquals(userDetails.getUsername(), "user");
	}

}
