package com.kavinunlimited.aathichudi;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@SpringBootApplication
public class TamilSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(TamilSchoolApplication.class, args);
	}
	
//	@Bean
//	public JmsListenerContainerFactory<?> myFactory(
//	    ConnectionFactory connectionFactory,
//	    DefaultJmsListenerContainerFactoryConfigurer configurer) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		
//		factory.setErrorHandler(t -> System.err.println("An error has occured in the transaction"));
//		configurer.configure(factory, connectionFactory);
//		return factory;
//	}
	
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}
