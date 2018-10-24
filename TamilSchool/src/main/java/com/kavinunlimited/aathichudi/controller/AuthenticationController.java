package com.kavinunlimited.aathichudi.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.request.ChangePasswordRequest;
import com.kavinunlimited.aathichudi.domain.request.ResetPasswordRequest;
import com.kavinunlimited.aathichudi.domain.request.ValidatePinRequest;
import com.kavinunlimited.aathichudi.domain.response.ForgotPasswordResponse;
import com.kavinunlimited.aathichudi.domain.response.TamilSchoolResponse;
import com.kavinunlimited.aathichudi.domain.response.UserProfileResponse;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.PinDoesNotMatchException;
import com.kavinunlimited.aathichudi.exception.TokenInvalidException;
import com.kavinunlimited.aathichudi.service.EmailService;
import com.kavinunlimited.aathichudi.service.UserService;
import com.kavinunlimited.aathichudi.utilities.cache.Token;
import com.kavinunlimited.aathichudi.utilities.cache.TokenService;
import com.kavinunlimited.aathichudi.utilities.email.Email;
import com.kavinunlimited.aathichudi.utilities.email.EmailTemplate;

@RestController
public class AuthenticationController {
	
	@Value("${from.email}") String FROM;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired TokenService tokenService;
	
	@Autowired UserService userService;
	
	@Autowired JmsTemplate jmsTemplate;
	
	@Autowired EmailService emailService;
	
	@Value("${login.url}") String LOGIN_URL;
	
	
	@CrossOrigin
	@RequestMapping(value="/user/changePassword", method=RequestMethod.POST) 
	public TamilSchoolResponse changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) throws AathichudiException {
		String username = authentication.getName();
		User user = this.userService.getUserByEmail(username);
		user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
		this.userService.saveUser(user);
		return new TamilSchoolResponse().setSuccess(true);
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/resetPassword/{email}", method=RequestMethod.POST)
	public TamilSchoolResponse resetPassword(@PathVariable("email") String email) {
		
		User user = this.userService.getUserByEmail(email);
		
		Random rand = new Random();
		String pin = String.format("%04d%n", rand.nextInt(10000));
		String tokenString = UUID.randomUUID().toString();
		Token token = new Token()
						.setToken(tokenString)
						.setEmail(email)
						.setExpiryDate(LocalDate.now().plusDays(1))
						.setPinMatched(false)
						.setNoOfFailedAttempts(0)
						.setPin(pin);
		this.tokenService.put(token);
		this.sendPinEmail(user, pin);
		return new ForgotPasswordResponse().setToken(tokenString).setSuccess(true);
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/validatePin", method=RequestMethod.POST)
	public TamilSchoolResponse validateToken(@Valid @RequestBody ValidatePinRequest request) throws AathichudiException {
		Token token = this.tokenService.isValid(request.getToken());
		if(token == null) {
			throw new TokenInvalidException();
		}
		if(!request.getEmail().equalsIgnoreCase(token.getEmail())) {
			throw new TokenInvalidException();
		}
		User user = this.userService.getUserByEmail(request.getEmail());
		if(token.getPin().equals(request.getPin())) {
			token.setPinMatched(true);
			this.tokenService.removeToken(token.getToken());
			token.setToken(UUID.randomUUID().toString());
			this.tokenService.put(token);
			return new TamilSchoolResponse().setSuccess(true);
		}
		int noOfFailedAttempts = token.getNoOfFailedAttempts() + 1;
		if(noOfFailedAttempts >= 3) {
			this.tokenService.removeToken(token.getToken());
		} else {
			token.setNoOfFailedAttempts(noOfFailedAttempts);
			this.tokenService.put(token);
		}
		throw new PinDoesNotMatchException();
	}
	
	@CrossOrigin
	@RequestMapping(value="/public/changePassword", method=RequestMethod.POST)
	public TamilSchoolResponse resetChangePassword(@Valid @RequestBody ResetPasswordRequest request) {
		String tokenString = request.getToken();
		Token token = this.tokenService.isValid(tokenString);
		if(token == null || !token.isPinMatched()) {
			throw new TokenInvalidException();
		}
		User user = this.userService.getUserByEmail(token.getEmail());
		user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
		this.tokenService.removeToken(tokenString);
		this.userService.saveUser(user);
		//Password Successfully changed.
		this.sendPasswordResetEmail(user);
		return new TamilSchoolResponse().setSuccess(true);
	}
	
	@CrossOrigin
	@RequestMapping(value="/user/profile", method=RequestMethod.GET)
	public TamilSchoolResponse login(Authentication authentication) {
		String username = authentication.getName();
		User user = this.userService.getUserByEmail(username);
		UserProfileResponse authResponse = new UserProfileResponse();
		authResponse.setRoles(user.getRoles());
		return authResponse.setSuccess(true);
	}
	
	private void sendPasswordResetEmail(User user) {
		EmailTemplate template = new EmailTemplate("passwordchangesuccess.html");
		HashMap<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", user.getFirstName());
		replacements.put("url", LOGIN_URL);
		String message = template.getTemplate(replacements);
		Email email = new Email(FROM, user.getEmail(), "Password Successfully Changed", message);
		email.setHtml(true);
		email.setHasAttachment(false);
		this.jmsTemplate.convertAndSend("EmailQueue", email);
	}
	
	private void sendPinEmail(User user, String pin) {
		EmailTemplate template = new EmailTemplate("forgotpassword.html");
		HashMap<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", user.getFirstName());
		replacements.put("pin", pin);
		String message = template.getTemplate(replacements);
		Email email = new Email(FROM, user.getEmail(), "Password Reset request", message);
		email.setHtml(true);
		email.setHasAttachment(false);
		this.jmsTemplate.convertAndSend("EmailQueue", email);
	}
	
}
