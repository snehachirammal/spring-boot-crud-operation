package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.service.UserService;

@Configuration
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable().httpBasic()
				.and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/books").hasRole("USER")
				.antMatchers(HttpMethod.GET,"/books**").hasRole("USER")
				.antMatchers(HttpMethod.GET,"/book/**").hasRole("USER")
				.antMatchers(HttpMethod.PATCH,"/book/**").hasAnyRole("USER","ADMIN")
				.antMatchers(HttpMethod.DELETE,"/book/**").hasRole("ADMIN")
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/").permitAll().and().formLogin();
		;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/h2-console/**");
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}