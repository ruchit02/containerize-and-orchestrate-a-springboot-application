package com.topnotch.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.topnotch.demo.models.Authority;
import com.topnotch.demo.models.DBUser;
import com.topnotch.demo.repositories.UserRepository;

public class TNUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repository ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println( "Searching for user in database ...." );
		Optional<DBUser> user = repository.findById( username ) ;
		
		if( !user.isPresent() ) {
			throw new BadCredentialsException("No such user present") ;
		}
		
		System.out.println( "User found ...." );
		DBUser dbUser = user.get() ;
		
		List<Authority> authorities = dbUser.getAuthorities() ;
		List<GrantedAuthority> gAuthorities = new ArrayList<>() ;
		
		for( Authority auth : authorities ) {
			
			gAuthorities.add( new SimpleGrantedAuthority( auth.getAuthority() ) ) ;
		}
		
		SecurityUser secUser = new SecurityUser();
		secUser.setAuthorities(gAuthorities);
		secUser.setUsername( dbUser.getUsername() );
		secUser.setPassword( dbUser.getPass_word() );
		
		return secUser;
	}

}
