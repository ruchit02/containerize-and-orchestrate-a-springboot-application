package com.topnotch.demo.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Service
public class JWTUtil {
	
	@Autowired
	private StreamBridge bridge ;
	
	@Autowired
	private Tracer tracer;
	
	private SecretKey key = Keys.secretKeyFor( SignatureAlgorithm.HS256 ) ;
	private String secret = null ;
	
	public JWTUtil() {
		super();
	}
	
	public void init() {
		
		secret = Encoders.BASE64URL.encode( key.getEncoded() );
		System.out.println( "Secret created : " + secret );
		
		System.out.println( "Sending Secret ...." );
		bridge.send( "secretExchange-out-0" ,  secret );
		System.out.println( "Secret Sent ...." );
	}

	public String getUsername(String token) {
		
		return this.getClaims(token).getSubject();
	}

	public Date getExpiration(String token) {
		
		return this.getClaims(token).getExpiration() ;
	}

	public boolean hasTokenExpired(String token) {
		
		return !this.getExpiration(token).before(new Date());
	}

	public boolean isValidToken(String token, UserDetails userDetails) {

		String username = this.getUsername(token);
		return username.equals(userDetails.getUsername()) && hasTokenExpired(token);
	}

	public Claims getClaims(String token) {
		
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody() ;
	}

	public String generateToken(String username) {

		//Map<String, Object> claims = new HashMap<>();
		return createToken(username);
	}

	private String createToken(String username) {
		
		Span span = tracer.buildSpan( "jwt-token" ).start();
		
		try( Scope scope = tracer.scopeManager().activate(span) ) {
			
			Date currentTime = new Date(System.currentTimeMillis()) ;
			Date expiryTime  = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12) ;
			
			Map<String, String> fields = new HashMap<>();
			fields.put( "Issuer" , "Ruchit Darji") ;
			fields.put( "Subject" , username ) ;
			fields.put( "Issued At" , currentTime.toString() );
			fields.put( "Expiry At" , expiryTime.toString() );
			span.log(fields);
			
			String token = Jwts.builder()
					   .setIssuer("Ruchit Darji")
					   .setSubject(username)
					   .setIssuedAt(currentTime)
					   .setExpiration(expiryTime)
					   .signWith(key)
					   .compact() ;
			
			 return token ;
		} finally {
			
			span.finish();
		}	   
	}
}
