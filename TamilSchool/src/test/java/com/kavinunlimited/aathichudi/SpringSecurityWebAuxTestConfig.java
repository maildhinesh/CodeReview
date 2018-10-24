package com.kavinunlimited.aathichudi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {
	
	@Bean
	@Primary
	public UserDetailsService userDetailService() {
//		User basicUser = new User().setEmail("user@test.com").setFirstName("Basic").setLastName("User").setStatus(UserStatus.ACTIVE);
//		Role userRole = new Role();
//		userRole.setRole("ROLE_USER");
//		basicUser.addRole(userRole);
//		LoggedInUser basicUserActive = new LoggedInUser(basicUser);
//		
//		User adminUser = new User().setEmail("admin@test.com").setFirstName("Admin").setLastName("User").setStatus(UserStatus.ACTIVE);
//		Role adminRole = new Role();
//		adminRole.setRole("ROLE_ADMIN");
//		adminUser.addRole(userRole);
//		adminUser.addRole(adminRole);
//		LoggedInUser adminUserActive = new LoggedInUser(adminUser);
//		List<UserDetails> users = new ArrayList<UserDetails>();
//		users.add(basicUserActive);
//		users.add(adminUserActive);
//		
//		return new InMemoryUserDetailsManager(users);
		
		List<SimpleGrantedAuthority> userRoles = new ArrayList<SimpleGrantedAuthority>();
		userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		List<SimpleGrantedAuthority> adminRoles = new ArrayList<SimpleGrantedAuthority>();
		userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
		userRoles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		User basicUser = new User("Basic User", "user@test.com", userRoles);
		User adminUser = new User("Admin User" , "admin@test.com", adminRoles);
		
		List<UserDetails> users = new ArrayList<UserDetails>();
		
		users.add(basicUser);
		users.add(adminUser);
		
		return new InMemoryUserDetailsManager(users);
	}

}
