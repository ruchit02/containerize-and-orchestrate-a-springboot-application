package com.topnotch.demo.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import com.topnotch.demo.utils.JWTUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

	@Autowired
	private JWTUtil jwtUtil ;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest() ;
		MultiValueMap<String, HttpCookie> cookieMap =  request.getCookies() ;
		
		HttpCookie cookie = cookieMap.getFirst( "jwtToken" ) ;
		
		if( cookie != null ) {
			
			String token = cookie.getValue() ;
			System.out.println( token );
			
			String userID = jwtUtil.getUsername(token);
			System.out.println( userID );
			
			if( jwtUtil.isValidToken(token) ) {
				
				request.mutate().header( "Authorization" , "Bearer " + token )
				.header( "UserId" , userID )
				.build() ;
			}
		}
		
		return chain.filter(exchange);
	}
}
