package com.security.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.inMemoryAuthentication().withUser("user")
			.password("{noop}password").roles("USER")
			.and()
			.withUser("admin").password("{noop}password")
			.roles("USER", "ADMIN");
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	
	http.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
//			TODO  create path for POST, PUT, DELETE...
			.and()
			.csrf().disable().formLogin().disable();
  }
  
  
}
