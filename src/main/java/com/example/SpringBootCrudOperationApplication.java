package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.model.User;
import com.example.repository.UserRepository;

@SpringBootApplication
public class SpringBootCrudOperationApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudOperationApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner demoData(UserRepository userRepo) {
        return args -> { 

        	userRepo.save(new User( "user", "user123",true, "ROLE_USER"));
        	userRepo.save(new User( "admin", "admin123",true, "ROLE_ADMIN"));
        
    };
}
}
