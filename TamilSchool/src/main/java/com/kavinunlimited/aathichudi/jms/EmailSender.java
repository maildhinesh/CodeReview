package com.kavinunlimited.aathichudi.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.kavinunlimited.aathichudi.service.EmailService;
import com.kavinunlimited.aathichudi.utilities.email.Email;

@Component
public class EmailSender {
	
	@Autowired EmailService emailService;
	
	@JmsListener(destination="EmailQueue")
	public void receiveMessage(Email email) {
		this.emailService.send(email);
	}
}
