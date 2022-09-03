package com.topnotch.demo.consumers;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.topnotch.demo.dtos.EmployeeDetailsDTO;
import com.topnotch.demo.services.TNPhotoDetailsService;

@Configuration
public class DocumentConsumer {

	@Autowired
	private TNPhotoDetailsService docService ;
	
	@Bean
	public Consumer<EmployeeDetailsDTO> extractDocumentDetails(){
		
		return employee -> {
			
			System.out.println( "Employee object received from message broker ...." );
			System.out.println( "Employee FirstName ...." + employee.getFirst_name() );
			System.out.println( "Employee LastName ...." + employee.getLast_name() );
			System.out.println( "Employee UserName ...." + employee.getDepartment() );
			System.out.println( "Employee UserName ...." + employee.getExpertise() );
			System.out.println( "Employee UserName ...." + employee.getEmail() );
			
			docService.createEmployeeAcc( employee );
		};
	}
}
