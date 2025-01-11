package com.eagle.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailService();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests -> requests
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/manager/**")
                .hasRole("MANAGER")
                .requestMatchers("/employee/**")
                .hasRole("EMPLOYEE")
                .requestMatchers("/")
                .authenticated()
                .requestMatchers("/reset")
                .permitAll()
                .requestMatchers("/new-pass")
                .permitAll()
                .requestMatchers("/response-page")
                .permitAll()
                .anyRequest()
                .authenticated())
                .formLogin(login -> login.loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll());
		
		return httpSecurity.build();
	}
}