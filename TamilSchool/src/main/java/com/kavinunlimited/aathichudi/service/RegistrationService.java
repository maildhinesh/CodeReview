package com.kavinunlimited.aathichudi.service;

import java.util.List;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationRequest;
import com.kavinunlimited.aathichudi.exception.AathichudiException;

public interface RegistrationService {
	
	public Registration saveRegistration(Registration registration) throws AathichudiException;
	
	public List<Registration> getAllRegistrations();
	
	public Registration getRegistrationById(String id) throws AathichudiException;

	public Registration createUserRegistration(CreateRegistrationRequest request) throws AathichudiException;

	public Registration validateRegistrationLink(String registrationId, String email) throws AathichudiException;
	
	public List<Registration> getAllRegistrationsByUser(User user) throws AathichudiException;
	
	public Registration getRegistrationByParent(User user, String id) throws AathichudiException;

}
