package com.topnotch.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.opentracing.Tracer;

@SpringBootApplication
//@EnableDiscoveryClient
public class PhotographyGatewayApplication {

	@Autowired
	private GatewayFilter authFilter ;
	
	@Value( "${com.topnotch.properties.service.protocol}" )
	private String TRANSFER_PROTOCOL;
	
	@Value("${com.topnotch.properties.authservice.host}")
	private String SERVICE_1_HOST ;
	
	@Value("${com.topnotch.properties.authservice.port}")
	private String SERVICE_1_PORT ;
	
	@Value("${com.topnotch.properties.docuploadservice.host}")
	private String SERVICE_2_HOST ;
	
	@Value("${com.topnotch.properties.docuploadservice.port}")
	private String SERVICE_2_PORT ;
	
	
	@Bean
	public Tracer initTracer() {
		
		SamplerConfiguration sampler = new SamplerConfiguration().withType("const").withParam(1);
		ReporterConfiguration reporter = new ReporterConfiguration().withLogSpans(true);
		Configuration config = new Configuration("gateway-service").withSampler(sampler).withReporter(reporter) ;
		
		return config.getTracer();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PhotographyGatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator gateway(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route( preSpec -> preSpec.method( HttpMethod.GET )
						  .and()
						  .path( "/myapp/gateway/endpoint1" )
				   .filters( filterSpec -> filterSpec.rewritePath( "/myapp/gateway/endpoint1" , "/myapp/authService/signupPage" ) )
				   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_1_HOST + ":" + SERVICE_1_PORT ) )
				
				.route( preSpec -> preSpec.method( HttpMethod.POST )
						  .and()
						  .path( "/myapp/gateway/endpoint1" )
				   .filters( filterSpec -> filterSpec.rewritePath( "/myapp/gateway/endpoint1" , "/myapp/authService/signupPage" ) )
				   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_1_HOST + ":" + SERVICE_1_PORT ) )
				
				.route( preSpec -> preSpec.method( HttpMethod.GET )
						  .and()
						  .path( "/myapp/gateway/endpoint2" )
				   .filters( filterSpec -> filterSpec.rewritePath( "/myapp/gateway/endpoint2" , "/myapp/authService/loginPage" ) )
				   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_1_HOST + ":" + SERVICE_1_PORT ) )
				
				.route( preSpec -> preSpec.method( HttpMethod.POST )
						  .and()
						  .path( "/myapp/gateway/endpoint2" )
				   .filters( filterSpec -> filterSpec.rewritePath( "/myapp/gateway/endpoint2" , "/myapp/authService/authenticate" ) )
				   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_1_HOST + ":" + SERVICE_1_PORT ) )
				
				.route( preSpec -> preSpec.method( HttpMethod.GET )
						  .and()
						  .path( "/myapp/gateway/endpointToken" )
				   .filters( filterSpec -> filterSpec.rewritePath( "/myapp/gateway/endpointToken" , "/myapp/authService/generateToken" ) )
				   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_1_HOST + ":" + SERVICE_1_PORT ) )
				
				.route( preSpec -> preSpec.method(HttpMethod.GET).and().path( "/myapp/gateway/endpoint3" )
							       .filters( filterSpec -> {
							    	   
							    	   return filterSpec.filter(authFilter)
							    	   		 .rewritePath( "/myapp/gateway/endpoint3" , "/myapp/serviceA/homePage" ) ;
							       })
							       .uri( TRANSFER_PROTOCOL + "://" + SERVICE_2_HOST + ":" + SERVICE_2_PORT ) )
				
				.route( preSpec -> preSpec.method(HttpMethod.POST).and().path( "/myapp/gateway/endpoint4" )
								   .filters( filterSpec -> {
									   return filterSpec.filter(authFilter)
											  .rewritePath( "/myapp/gateway/endpoint4" , "/myapp/serviceA/uploadPage" ) ;
								   }) 
								   .uri( TRANSFER_PROTOCOL + "://" + SERVICE_2_HOST + ":" + SERVICE_2_PORT ) )
				.build() ;
	}
}
