package com.kavinunlimited.aathichudi.utilities.cache;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TokenService {
	
	private HashMap<String, Token> map = new HashMap<String, Token>();
	
	public void put(Token token) {
		this.map.put(token.getToken(), token);
	}
	
	public Token isValid(String token) {
		Token tokenObj = this.map.get(token);
		if(tokenObj == null) {
			return null;
		}
		LocalDate now = LocalDate.now();
		if(tokenObj.getExpiryDate().isBefore(now)) {
			this.map.remove(token);
			return null;
		}
		return tokenObj;
	}
	
	public void removeToken(String token) {
		this.map.remove(token);
	}
	
	public void cleanMap() {
		Set<String> tokens = this.map.keySet();
		for(String token: tokens) {
			Token tokenObj = this.map.get(token);
			if(tokenObj.getExpiryDate().isBefore(LocalDate.now())) {
				this.map.remove(token);
			}
		}
	}
}
