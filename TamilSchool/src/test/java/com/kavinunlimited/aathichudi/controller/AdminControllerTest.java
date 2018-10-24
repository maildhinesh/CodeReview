package com.kavinunlimited.aathichudi.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationStudent;
import com.kavinunlimited.aathichudi.exception.UserNotFoundException;
import com.kavinunlimited.aathichudi.service.RegistrationService;
import com.kavinunlimited.aathichudi.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		)
@AutoConfigureMockMvc
public class AdminControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean 
	UserService userService;
	
	@MockBean
	RegistrationService registrationService;
	
	private Registration registration;
	
	@Before
	public void setup() {
		registration = new Registration().setAathichudiGrade(AathichudiGrade.NILAI_1).setStudent(new Student().setFirstName("Mukilan")).setId("REGISTRATION1");
		List<Registration> registrations = Arrays.asList(registration);
		Mockito.when(registrationService.getAllRegistrations()).thenReturn(registrations);
		
		Mockito.when(userService.getUserByEmail("newuser@test.com")).thenThrow(UserNotFoundException.class);
		Mockito.when(registrationService.getRegistrationById("REGISTRATION1")).thenReturn(registration);
		CreateRegistrationRequest request = new CreateRegistrationRequest();
		request.setEmail("newuser@test.com");
		request.setFirstName("Parent");
		request.setLastName("User");
		CreateRegistrationStudent student = new CreateRegistrationStudent();
		student.setFirstName("Mukilan");
		student.setLastName("Dhinesh");
		student.setAathichudiGrade(AathichudiGrade.NILAI_1);
		request.setStudent(student);
		//Mockito.when(registrationService.createUserRegistration(any(CreateRegistrationRequest.class))).thenReturn(registration);
		
		User user = new User().setEmail("newuser@test.com").setFirstName("New").setLastName("User");
		Role role = new Role();
		role.setRole("ROLE_USER");
		user.addRole(role);
		Mockito.when(userService.getUserByEmail("existinguser@test.com")).thenReturn(user);
		Mockito.when(userService.createUser("New", "User", "newuser@test.com", "password", "ROLE_USER")).thenReturn(user);
	}
	//UT001
	@Test
	@WithMockUser(username="admin@test.com", password="test", roles="ADMIN")
	public void whenGetAllAdminRegistrations_thenReturnRegistrations() throws Exception {
		
		mvc.perform(get("/admin/getAllRegistrations")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].aathichudiGrade").value("Nilai 1"))
				.andExpect(jsonPath("$[0].student.firstName").value("Mukilan"));
	}
	//UT002
	@Test
	@WithMockUser(username="admin@test.com", password="test", roles="ADMIN")
	public void whenCreateUserValidInput_thenCreateAndReturnUser() throws Exception {
		String requestJSON = "{" + 
							"\"email\" : \"newuser@test.com\"," + 
							"\"firstName\": \"New\"," + 
							"\"lastName\": \"User\"," + 
							"\"password\": \"password\"," + 
							"\"role\" : { \"role\" : \"ROLE_USER\"}" + 
							"}";
		
		mvc.perform(post("/admin/register/user").accept(MediaType.APPLICATION_JSON).content(requestJSON).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email").value("newuser@test.com"))
			.andExpect(jsonPath("$.roles", hasSize(1)))
			.andExpect(jsonPath("$.roles[0].role").value("ROLE_USER"));
	}
	//UT003
	@Test
	@WithMockUser(username="admin@test.com", password="test", roles="ADMIN")
	public void whenCreateUserExistingUser_thenThrowUserAlreadyExistsException() throws Exception {
		String requestJSON = "{" + 
				"\"email\" : \"existinguser@test.com\"," + 
				"\"firstName\": \"New\"," + 
				"\"lastName\": \"User\"," + 
				"\"password\": \"password\"," + 
				"\"role\" : { \"role\" : \"ROLE_USER\"}" + 
				"}";

		mvc.perform(post("/admin/register/user").accept(MediaType.APPLICATION_JSON).content(requestJSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.code").value("400.002"))
				.andExpect(jsonPath("$.message").value("Parent account already exists. Ask parent to login to his/her account"));
	}
	
	@Test
	@WithMockUser(username="admin@test.com",password="test", roles="ADMIN")
	public void whenGetRegistrationById_thenReturnREgistration() throws Exception {
		mvc.perform(get("/admin/getRegistrationById/REGISTRATION1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("REGISTRATION1"))
				.andExpect(jsonPath("$.student.firstName").value("Mukilan"));
	}
	
	@Test
	@WithMockUser(username="admin@test.com", password="test", roles="ADMIN")
	public void whenCreateRegistrationWithValidInput_thenReturnRegistration() throws Exception {
		String requestJSON = "{" + 
								"\"email\": \"newuser@test.com\"," + 
								"\"firstName\": \"Parent\"," + 
								"\"lastName\": \"User\"," + 
								"\"student\": {" + 
									"\"firstName\": \"Mukilan\"," + 
									"\"lastName\": \"Dhinesh\"," + 
									"\"aathichudiGrade\": \"Nilai 1\"" + 
								"}" + 
							"}";
		mvc.perform(post("/admin/createUser").content(requestJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("REGISTRATION1"))
				.andExpect(jsonPath("$.student.firstName").value("Mukilan"));
	}

}
