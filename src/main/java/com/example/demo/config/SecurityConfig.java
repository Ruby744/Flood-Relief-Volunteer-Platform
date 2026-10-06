package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//Refer to https://www.baeldung.com/spring-security-login
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .requestMatchers("/admin").authenticated()
	            .requestMatchers("/admin-user/**").authenticated()
	            .requestMatchers("/admin-project/**").authenticated()
	            .requestMatchers("/admin-donation/**").authenticated()
	            .requestMatchers("/admin-volunteer/**").authenticated()
	            .requestMatchers("/admin-login").permitAll()
	            .anyRequest().permitAll()
	        .and()
	        .formLogin()
	            .loginPage("/admin-login") // Custom login page
	            .loginProcessingUrl("/login") // Form submission URL
	            .defaultSuccessUrl("/admin", true) // Redirect on successful login
	            .failureUrl("/admin-login?error=true") // Redirect on login failure
	            .permitAll()
	        .and()
	        .logout()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/admin-login")
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .permitAll();
	    return http.build();
	}

}
