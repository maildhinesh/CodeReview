package com.kavinunlimited.aathichudi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.RegistrationEntity;
import com.kavinunlimited.aathichudi.domain.response.TamilSchoolResponse;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.service.RegistrationService;
import com.kavinunlimited.aathichudi.service.UserService;

@RestController
public class UserController {
	
	@Autowired UserService userService;
	
	@Autowired RegistrationService registrationService;
	
	@CrossOrigin
	@RequestMapping(value="/user/getRegistrations", method=RequestMethod.GET)
	public List<RegistrationEntity> getRegistrations(Authentication authentication) throws AathichudiException {
		List<RegistrationEntity> response = new ArrayList<RegistrationEntity>();
		String username = authentication.getName();
		User user = this.userService.getUserByEmail(username);
		List<Registration> registrations = this.registrationService.getAllRegistrationsByUser(user);
		for(Registration registration : registrations) {
			RegistrationEntity registrationEntity = new RegistrationEntity().map(registration);
			response.add(registrationEntity);
		}
		return response;
	}
	
	@CrossOrigin
	@RequestMapping(value="/user/getRegistration/{id}", method=RequestMethod.GET)
	public RegistrationEntity getRegistration(Authentication authentication, @PathVariable(name="id") String id) throws AathichudiException {
		User user = this.userService.getUserByEmail(authentication.getName());
		Registration registration = this.registrationService.getRegistrationByParent(user, id);
		return new RegistrationEntity().map(registration);
	}
	
	@CrossOrigin
	@RequestMapping(value="/user/registration", method=RequestMethod.POST) 
	public TamilSchoolResponse saveRegistration(@RequestBody RegistrationEntity registrationEntity) throws AathichudiException {
		Registration registration = new Registration().map(registrationEntity);
		this.registrationService.saveRegistration(registration);
		return new TamilSchoolResponse().setSuccess(true);
	}

}
