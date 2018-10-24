package com.kavinunlimited.aathichudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.repository.RegistrationRepository;
import com.kavinunlimited.aathichudi.dao.repository.RoleRepository;
import com.kavinunlimited.aathichudi.dao.repository.UserRepository;
import com.kavinunlimited.aathichudi.domain.RegistrationEntity;
import com.kavinunlimited.aathichudi.domain.request.AddRoleRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateParentLoginRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateUserRequest;
import com.kavinunlimited.aathichudi.domain.request.UserRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.request.ValidateRegistrationLinkRequest;
import com.kavinunlimited.aathichudi.domain.response.TamilSchoolResponse;
import com.kavinunlimited.aathichudi.domain.response.ValidateRegistrationLinkResponse;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.DBException;
import com.kavinunlimited.aathichudi.exception.DatabaseException;
import com.kavinunlimited.aathichudi.exception.InvalidRegistrationException;
import com.kavinunlimited.aathichudi.exception.RegistrationNotFoundException;
import com.kavinunlimited.aathichudi.exception.UserAlreadyExistsException;
import com.kavinunlimited.aathichudi.exception.UserNotFoundException;
import com.kavinunlimited.aathichudi.service.RegistrationService;
import com.kavinunlimited.aathichudi.service.UserService;

@RestController
public class RegisterUserController {
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired UserService userService;
	
	@Autowired RegistrationService registrationService;
	
	@CrossOrigin
	@RequestMapping(value="/public/validate", method=RequestMethod.POST)
	public TamilSchoolResponse validateRegistration(@RequestBody ValidateRegistrationLinkRequest request) throws AathichudiException {
		
			Registration registration = this.registrationService.validateRegistrationLink(request.getToken(), request.getEmail());
			if(registration != null)
			{
				ValidateRegistrationLinkResponse response = new ValidateRegistrationLinkResponse()
																.setRegistrationStatus(registration.getStatus());
				try {
					User user = this.userService.getUserByEmail(request.getEmail());
					response.setNewUser(false);
				}catch(UserNotFoundException ex) {
					response.setNewUser(true);
				}
				return response;
			}
			throw new RegistrationNotFoundException(); 
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/createUser", method=RequestMethod.POST)
	public TamilSchoolResponse createUser(@RequestBody CreateUserRequest request) throws AathichudiException{
		
		Registration registration = this.registrationService.validateRegistrationLink(request.getToken(), request.getEmail());
		if(registration != null) { 
			User user = this.userService.createUser(request);
			if(user != null) { 
				return new TamilSchoolResponse().setSuccess(true);
			}
		} else { 
			throw new RegistrationNotFoundException();
		}
		throw new DBException();
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/getRegistration/{id}", method=RequestMethod.GET)
	public RegistrationEntity getRegistrationById(@PathVariable("id") String id) throws AathichudiException {
		Registration registration = this.registrationService.getRegistrationById(id);
		RegistrationEntity response = new RegistrationEntity().map(registration).setUserCreated(false);
		try {
			User user = this.userService.getUserByEmailIgnoresException(registration.getStudent().getParents().get(0).getEmail());
			if(user != null) {
				response.setUserCreated(true);
			}
			return response;
		}catch (NullPointerException ex) {
			throw new InvalidRegistrationException();
		}
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/createParentLogin", method=RequestMethod.POST)
	public TamilSchoolResponse createParentLogin(@RequestBody CreateParentLoginRequest request) throws AathichudiException {
		Registration registration = this.registrationService.getRegistrationById(request.getRegistrationId());
		try {
			User user = this.userService.getUserByEmailIgnoresException(registration.getStudent().getParents().get(0).getEmail());
			if(user != null) {
				throw new UserAlreadyExistsException();
			}
			Parent parent = registration.getStudent().getParents().get(0);
			user = this.userService.createUser(parent.getFirstName(), parent.getLastName(), parent.getEmail(), request.getPassword(), "ROLE_USER");
			user.setParent(parent);
			this.userService.saveUser(user);
		}catch(Exception ex) {
			throw new DatabaseException();
		}
		return new TamilSchoolResponse().setSuccess(true);
	}

}
