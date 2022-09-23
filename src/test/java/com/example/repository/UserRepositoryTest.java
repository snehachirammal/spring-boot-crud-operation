package com.example.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.User;

@RunWith(SpringJUnit4ClassRunner.class) 
@Transactional
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application-test.properties")
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
    public void findByUserNameSuccessTest() {
        User user = new User("sneha","pass",true,"USER");
        userRepository.save(user);
        Optional<User> actualResult = userRepository.findByUserName("sneha");
        assertEquals(actualResult.get(), user);
    }
	@Test
    public void findByUserNameFailureTest() {
        User user = new User("sneha","pass",true,"USER");
        userRepository.save(user);
        Optional<User> actualResult = userRepository.findByUserName("sneha");
        assertNotEquals(actualResult.get(), new User());
    }
	

}
