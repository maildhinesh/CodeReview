package com.kavinunlimited.aathichudi.authentication;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.domain.LoggedInUser;

import io.jsonwebtoken.Jwts;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
								   HttpServletResponse res,
								   FilterChain chain) throws IOException, ServletException {
		String header = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req,res);
			return;
		}
		
		TokenBasedAuthentication authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
		
	}
	
	private TokenBasedAuthentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            String username = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET.getBytes())
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (username != null) {
            	UserDetails user = this.userDetailsService.loadUserByUsername(username);
            	TokenBasedAuthentication authentication = new TokenBasedAuthentication(user);
            	authentication.setToken(token.replace(SecurityConstants.TOKEN_PREFIX,  ""));
            	return authentication;
            }
            return null;
        }
        return null;
    }

}
