package com.kavinunlimited.aathichudi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TamilSchoolApplicationTests {
	
	@Mock
	JmsTemplate jmsTemplate;
	
	private JmsMessagingTemplate messagingTemplate;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.messagingTemplate = new JmsMessagingTemplate(this.jmsTemplate);
	}

	@Test
	public void contextLoads() {
		
	}

}
