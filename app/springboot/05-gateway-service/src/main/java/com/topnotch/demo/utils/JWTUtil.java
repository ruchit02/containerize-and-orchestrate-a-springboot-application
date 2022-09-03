package com.topnotch.demo.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {
	
	private SecretKey key = null ;
	
	public JWTUtil() {
		super();
	}
	
	public void init(String secret) {
		
		key = Keys.hmacShaKeyFor( Decoders.BASE64URL.decode(secret) ) ;
	}
	
	public String getUsername(String token) {
		
		if( key == null ) { return null ; }
		
		return this.getClaims(token).getSubject();
	}

	public Date getExpiration(String token) {
		
		if( key == null ) { return null ; }
		
		return this.getClaims(token).getExpiration();
	}

	private boolean hasTokenExpired(String token) {

		return !this.getExpiration(token).before(new Date());
	}
	
	public boolean isValidToken(String token) {
		
		if( key == null ) { return false ; }
		
		return hasTokenExpired(token);
	}

	private Claims getClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
