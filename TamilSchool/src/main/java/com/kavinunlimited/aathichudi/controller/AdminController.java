package com.kavinunlimited.aathichudi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.RegistrationEntity;
import com.kavinunlimited.aathichudi.domain.request.AddRoleRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.request.UserRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.response.TamilSchoolResponse;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.DatabaseException;
import com.kavinunlimited.aathichudi.exception.UserAlreadyExistsException;
import com.kavinunlimited.aathichudi.exception.UserNotFoundException;
import com.kavinunlimited.aathichudi.service.RegistrationService;
import com.kavinunlimited.aathichudi.service.UserService;
import com.kavinunlimited.aathichudi.utilities.email.Email;
import com.kavinunlimited.aathichudi.utilities.email.EmailTemplate;

@RestController
public class AdminController {
	
	@Value("${registration.url}")
	private String REGISTRATION_URL;
	
	@Autowired
	UserService userService;
	
	@Autowired RegistrationService registrationService;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@CrossOrigin
	@RequestMapping(value="/admin/register/user", method=RequestMethod.POST)
	public User registerUser(@RequestBody UserRegistrationRequest userRequest) throws AathichudiException {
		User user = null;
		try { 
			user = this.userService.getUserByEmail(userRequest.getEmail());
			if(user != null) {
				throw new UserAlreadyExistsException();
			}
		}catch(UserNotFoundException ex) {
			user = this.userService.createUser(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole().getRole());
		}
		return user;
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/role/add", method=RequestMethod.POST)
	public Role registerRole(@RequestBody AddRoleRequest request) throws Exception {
		return this.userService.addRole(request.getRole(), request.getRoleDescription());
	}
	
	@CrossOrigin
	@RequestMapping(value = "/admin/createUser", method=RequestMethod.POST)
	public TamilSchoolResponse createuser(@Valid @RequestBody CreateRegistrationRequest request) throws AathichudiException {
		
		Registration registration = this.registrationService.createUserRegistration(request);
		if(registration != null) {
			sendEmail(registration, request.getEmail(), request.getFirstName());
			return new TamilSchoolResponse().setSuccess(true);
		}
		throw new DatabaseException();
		
	}
	
	@CrossOrigin
	@RequestMapping(value="/admin/getAllRegistrations", method=RequestMethod.GET)
	public List<RegistrationEntity> getAllRegistrations(Authentication authentication) throws AathichudiException {
		List<RegistrationEntity> response = new ArrayList<RegistrationEntity>();
		List<Registration> registrations = this.registrationService.getAllRegistrations();
		for(Registration registration : registrations) {
			response.add(new RegistrationEntity().map(registration));
		}
		return response;
	}
	
	@CrossOrigin
	@RequestMapping(value="/admin/getRegistrationById/{id}", method=RequestMethod.GET) 
	public RegistrationEntity getRegistrationById(@PathVariable("id") String id) throws AathichudiException {
		Registration registration = this.registrationService.getRegistrationById(id);
		return new RegistrationEntity().map(registration);
	}
	
	private void sendEmail(Registration registration, String emailAddress, String firstName) {
		EmailTemplate template = new EmailTemplate("registrationinvite.html");
		HashMap<String, String> replacement = new HashMap<String, String>();
		replacement.put("user", firstName);
		replacement.put("link", REGISTRATION_URL+"/"+registration.getId());
		String message = template.getTemplate(replacement);
		Email email = new Email("events@thetamilsofgroc.org", emailAddress, "Complete Registration of your kid(s) in Aathichudi Arunthamizh Palli", message);
		email.setHtml(true);
		email.setHasAttachment(false);
		jmsTemplate.convertAndSend("EmailQueue", email);
	}
}
