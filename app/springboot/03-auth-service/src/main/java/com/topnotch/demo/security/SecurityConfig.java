package com.topnotch.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topnotch.demo.services.TNUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Value("${com.topnotch.properties.gatewayservice.host}")
	private String GATEWAY_HOST ;
	
	@Value("${com.topnotch.properties.gatewayservice.port}")
	private String GATEWAY_PORT ;
	
	@Value("${server.port}")
	private String SERVER_PORT;
	
	@Value("${com.topnotch.properties.gatewayservice.transferprotocol}")
	private String TRANSFER_PROTOCOL;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new TNUserDetailsService() ;
	}
	
	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder() ;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( userDetailsService() ).passwordEncoder( myPasswordEncoder() ) ;
	}
	
	@Bean(name="myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager() ;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//"/myapp/authService/generateToken"
		String successURL = TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpointToken" ;
		
		http.csrf().disable()
			.authorizeRequests().antMatchers( "/myapp/authService/**", "/actuator", "/actuator/*" ).permitAll()			
								.anyRequest().authenticated() 
	    .and()
	        .formLogin()
		    .loginPage( "/myapp/authService/loginPage" ).permitAll()
		    .loginProcessingUrl( "/myapp/authService/authenticate" )
		    .defaultSuccessUrl( successURL , true)
	    .and()
	        .logout();
	}
}

