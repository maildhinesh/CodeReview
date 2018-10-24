package com.kavinunlimited.aathichudi.service.impl;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.entity.enums.UserStatus;
import com.kavinunlimited.aathichudi.dao.repository.ParentRepository;
import com.kavinunlimited.aathichudi.dao.repository.RoleRepository;
import com.kavinunlimited.aathichudi.dao.repository.UserRepository;
import com.kavinunlimited.aathichudi.domain.request.CreateUserRequest;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.InvalidRegistrationException;
import com.kavinunlimited.aathichudi.exception.UserAlreadyExistsException;
import com.kavinunlimited.aathichudi.exception.UserNotFoundException;
import com.kavinunlimited.aathichudi.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ParentRepository parentRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	@Override
	public User createUser(CreateUserRequest request) throws AathichudiException {
		Role userRole = this.roleRepository.findOneByRole("ROLE_USER");
		Optional<User> opt = this.userRepository.findOneByEmail(request.getEmail());
		if(opt.isPresent()) {
			throw new UserAlreadyExistsException();
		}
		Optional<Parent> optParent = this.parentRepository.findOneByEmail(request.getEmail());
		if(!optParent.isPresent()) {
			throw new InvalidRegistrationException();
		}
		Parent parent = optParent.get();
		User user = new User();
		user
			.setId(UUID.randomUUID().toString())
			.setEmail(request.getEmail())
			.setPassword(this.passwordEncoder.encode(request.getPassword()))
			.setCreatedDate(LocalDate.now())
			.addRole(userRole)
			.setFirstName(parent.getFirstName())
			.setLastName(parent.getLastName())
			.setParent(parent)
			.setStatus(UserStatus.ACTIVE);
		this.userRepository.save(user);
		return user;
	}

	@Override
	public User getUserByEmail(String email) throws AathichudiException {
		Optional<User> user = this.userRepository.findOneByEmail(email);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserNotFoundException();
	}
	
	@Override
	public User getUserByEmailIgnoresException(String email) throws AathichudiException {
		try {
			User user = this.getUserByEmail(email);
			return user;
		}catch(UserNotFoundException ex) {
			return null;
		}
	}

	@Override
	public User createUser(String firstName, String lastName, String email, String password, String role)
			throws AathichudiException {
		// TODO Auto-generated method stub
		User user = new User()
					.setId(UUID.randomUUID().toString())
					.setFirstName(firstName)
					.setLastName(lastName)
					.setEmail(email)
					.setPassword(this.passwordEncoder.encode(password))
					.setCreatedDate(LocalDate.now())
					.setStatus(UserStatus.ACTIVE);
		Role userRole = this.getRoleByRoleName(role);
		user.addRole(userRole);
		if(role.equals("ROLE_ADMIN") || role.equals("ROLE_TEACHER")) {
			Role additionalRole = this.getRoleByRoleName("ROLE_USER");
			user.addRole(additionalRole);
		}
		this.userRepository.save(user);
		return user;
	}
	
	private Role getRoleByRoleName(String roleName) throws AathichudiException {
		Role role = this.roleRepository.findOneByRole(roleName);
		if(role == null) {
			throw new AathichudiException("404.100", "Role Not found in database", HttpStatus.NOT_FOUND);
		}
		return role;
	}
	
	@Override
	public User saveUser(User user) {
		this.userRepository.save(user);
		return user;
	}
	
	@Override
	public Role addRole(String role, String roleDescription) throws AathichudiException {
		Role newRole = this.roleRepository.findOneByRole(role);
		if(newRole != null) {
			throw new AathichudiException("400.100", "Role already exists", HttpStatus.CONFLICT);
		}
		newRole = new Role();
		newRole.setId(UUID.randomUUID().toString());
		newRole.setRole(role);
		newRole.setRoleDescription(roleDescription);
		this.roleRepository.save(newRole);
		return newRole;
	}

}
