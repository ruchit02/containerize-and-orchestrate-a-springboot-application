package com.topnotch.demo.consumers;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.topnotch.demo.utils.JWTUtil;

@Configuration
public class SecretConsumer {

	@Autowired
	private JWTUtil jwtUtil ;
	
	@Bean
	public Consumer<String> secretReceiver(){
		
		return secret -> {
			
			System.out.println( "Secret Received : " + secret );
			jwtUtil.init(secret);
		};
	}
}
