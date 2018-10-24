package com.kavinunlimited.aathichudi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.repository.UserRepository;
import com.kavinunlimited.aathichudi.domain.LoggedInUser;

@Service
@Transactional
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = this.userRepository.findOneByEmail(username);
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("No such user");
		}
		LoggedInUser loggedUser = new LoggedInUser(user.get());
		return loggedUser;
	}

}
