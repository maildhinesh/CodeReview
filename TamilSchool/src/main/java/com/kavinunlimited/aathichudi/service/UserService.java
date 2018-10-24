package com.kavinunlimited.aathichudi.service;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.request.CreateUserRequest;
import com.kavinunlimited.aathichudi.exception.AathichudiException;

public interface UserService {
	
	public User createUser(CreateUserRequest request) throws AathichudiException;
	
	public User getUserByEmail(String email) throws AathichudiException;
	
	public User getUserByEmailIgnoresException(String email) throws AathichudiException;
	
	public User createUser(String firstName, String lastName, String email, String password, String role) throws AathichudiException;
	
	public User saveUser(User user);
	
	public Role addRole(String role, String roleDescription) throws AathichudiException;

}
