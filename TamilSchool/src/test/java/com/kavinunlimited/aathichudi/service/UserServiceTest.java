package com.kavinunlimited.aathichudi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.kavinunlimited.aathichudi.UnitTestDataEngine;
import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.repository.ParentRepository;
import com.kavinunlimited.aathichudi.dao.repository.RoleRepository;
import com.kavinunlimited.aathichudi.dao.repository.UserRepository;
import com.kavinunlimited.aathichudi.domain.request.CreateUserRequest;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.InvalidRegistrationException;
import com.kavinunlimited.aathichudi.exception.UserAlreadyExistsException;
import com.kavinunlimited.aathichudi.exception.UserNotFoundException;
import com.kavinunlimited.aathichudi.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {
		@Bean
		public UserService userService() {
			UserServiceImpl impl = new UserServiceImpl();
			return impl;
		}
		
		@Bean
		public UnitTestDataEngine testDataEngine() {
			return new UnitTestDataEngine();
		}
	}
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	UnitTestDataEngine testDataEngine;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ParentRepository parentRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	private final String PASSWORD = "testpassword";
	
	private final String TEST_DATA_PREFIX = "testdata/unit/userservice/";
	
	@Before
	public void setup() {
		try {
			User user = new User().setEmail("user@test.com");
			Mockito.when(userRepository.findOneByEmail(user.getEmail())).thenReturn(Optional.of(user));
			Mockito.when(userRepository.save(user)).thenReturn(user);
			
			
			Parent parent = new Parent().setEmail("parent@test.com").setFirstName("Dhinesh").setLastName("Kumararaman");
			Mockito.when(parentRepository.findOneByEmail(parent.getEmail())).thenReturn(Optional.of(parent));
			
			Role adminRole = new Role();
			adminRole.setRole("ROLE_ADMIN");
			
			Role userRole = new Role();
			userRole.setRole("ROLE_USER");
			
			Role teacherRole = new Role();
			teacherRole.setRole("ROLE_TEACHER");
			
			Mockito.when(roleRepository.findOneByRole(adminRole.getRole())).thenReturn(adminRole);
			Mockito.when(roleRepository.findOneByRole(userRole.getRole())).thenReturn(userRole);
			Mockito.when(roleRepository.findOneByRole(teacherRole.getRole())).thenReturn(teacherRole);
			
			Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
		}catch(Exception e) {
			System.out.println("Could not continue testing");
		}
	}
	//UT001
	@Test
	public void whenValidEmail_thenUserShouldBeFound() {
		String email = "user@test.com";
		User found = userService.getUserByEmail(email);
		assertThat(found.getEmail()).isEqualTo(email);
	}
	
	//UT002
	@Test(expected = UserNotFoundException.class)
	public void whenInvalidEmail_thenExceptionShouldFire() {
		String email = "invaliduser@test.com";
		userService.getUserByEmail(email);
	}
	
	//UT003
	@Test
	public void whenInvalidEmail_thenExceptionShouldNotFire() {
		String email = "invaliduser@test.com";
		User user = userService.getUserByEmailIgnoresException(email);
		assertThat(user).isNull();
	}
	
	//UT004
	@Test
	public void whenInputValid_thenCreateUser() throws IOException{
		CreateUserRequest request = this.testDataEngine.getUnitTestData(TEST_DATA_PREFIX + "ut004.json", CreateUserRequest.class);
		
		User user = userService.createUser(request);
		assertThat(user.getEmail()).isEqualTo(request.getEmail());
		assertThat(user.getParent()).isNotNull();
		assertThat(user.getParent().getEmail()).isEqualTo(request.getEmail());
	}
	
	//Negative Tests
	//UT005
	@Test(expected = UserAlreadyExistsException.class)
	public void whenUserAlreadyExists_thenThrowsUserAlreadyExistsException() throws IOException {
		CreateUserRequest request = this.testDataEngine.getUnitTestData(TEST_DATA_PREFIX + "ut005.json", CreateUserRequest.class);
		
		User user = this.userService.createUser(request);
	}
	
	//UT006
	@Test(expected = InvalidRegistrationException.class)
	public void whenParentNotFoundInDB_thenThrowInvalidRegistrationException() throws IOException {
		CreateUserRequest request = this.testDataEngine.getUnitTestData(TEST_DATA_PREFIX + "ut006.json", CreateUserRequest.class);
		userService.createUser(request);
	}
	
	//UT007
	@Test
	public void whenCreateAdmin_thenUserRoleIsAlsoPresent() {
		User user = this.userService.createUser("Admin", "User", "admin@test.com", "password", "ROLE_ADMIN");
		assertThat(user.getRoles()).hasSize(2);
		assertThat(user.getRoles().get(0).getRole()).contains("ROLE_ADMIN");
		assertThat(user.getRoles().get(1).getRole()).contains("ROLE_USER");
	}
	
	//UT008
	@Test
	public void whenCreateTeacher_thenUserRoleisAlsoPresent() {
		User user = this.userService.createUser("Admin", "User", "teacher@test.com", "password", "ROLE_TEACHER");
		assertThat(user.getRoles()).hasSize(2);
		assertThat(user.getRoles().get(0).getRole()).contains("ROLE_TEACHER");
		assertThat(user.getRoles().get(1).getRole()).contains("ROLE_USER");
	}
	
	//UT009
	@Test
	public void whenNonExistingRole_thenRoleCreationSuccessful() {
		Role role = this.userService.addRole("ROLE_NEWROLE", "New role for unit testing");
		assertThat(role).isNotNull();
		assertThat(role.getRole()).isEqualTo("ROLE_NEWROLE");
	}
	
	//UT010
	@Test(expected=AathichudiException.class)
	public void whenExistingRole_thenThrowsAathichudiException() {
		Role role = this.userService.addRole("ROLE_USER", "Testing");
	}
	
	
}
