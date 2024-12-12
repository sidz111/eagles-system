package com.eagle.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eagle.entities.User;
import com.eagle.repository.UserRepository;


@Component  // for allowing to make object use @Component annotation
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Admin Not found");
		}
		else {
			return new CustomUser(user);
		}
	}

}