package com.nghiale.api.utils;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private static final String SECRET_KEY = "mysecretkey";

	private static final long EXP_TIME = 60 * 60 * 1000;

	public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + EXP_TIME)).compact();
	}

	public boolean isExpiration(String token) {
		if (getExpiration(token).before(new Date(System.currentTimeMillis()))) {
			return true;
		}
		return false;
	}

	public Date getExpiration(String token) {
		return getByToken(token, Claims::getExpiration);
	}

	public String getUsername(String token) {
		return getByToken(token, Claims::getSubject);
	}

	public <T> T getByToken(String token, Function<Claims, T> function) {
		final Claims claims = getAllClaims(token);
		return function.apply(claims);
	}

	public Claims getAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = userDetails.getUsername();
		if (!isExpiration(token) && getUsername(token).equals(username)) {
			return true;
		}
		return false;
	}

	public String doGenerateToken(UserDetails userDetails) {
		final Map<String, Object> claims = new HashMap<String, Object>();
		return generateToken(claims, userDetails);
	}
}
