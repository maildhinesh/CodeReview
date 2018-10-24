package com.kavinunlimited.aathichudi.authentication;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavinunlimited.aathichudi.domain.LoggedInUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.kavinunlimited.aathichudi.authentication.SecurityConstants.EXPIRATION_TIME;
import static com.kavinunlimited.aathichudi.authentication.SecurityConstants.SECRET;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
										HttpServletResponse res,
										Authentication authentication) {
		clearAuthenticationAttributes(req);
		LoggedInUser user = (LoggedInUser) authentication.getPrincipal();
		ArrayList<String> roles = new ArrayList<String>();
		for(GrantedAuthority authority : user.getAuthorities()) {
			roles.add(authority.getAuthority());
		}
		Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
		String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
		UserTokenState userTokenState = new UserTokenState(token, expiration.getTime());
		try {
			String jwtResponse = new ObjectMapper().writeValueAsString(userTokenState);
			res.setContentType("application/json");
			res.getWriter().write(jwtResponse);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private class UserTokenState {
		private String jws;
		private long expires;
		public UserTokenState (String jws, long expires) {
			this.jws = jws;
			this.expires = expires;
		}
		public String getJws() {
			return jws;
		}
		public void setJws(String jws) {
			this.jws = jws;
		}
		public long getExpires() {
			return expires;
		}
		public void setExpires(long expires) {
			this.expires = expires;
		}
		
	}

}
