package com.topnotch.demo.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.topnotch.demo.dtos.DocUploadResponse;
import com.topnotch.demo.models.EmployeeDetails;
import com.topnotch.demo.repositories.EmployeeDetailsRepository;
import com.topnotch.demo.services.TNPhotoDetailsService;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
@RequestMapping("/myapp/serviceA")
public class SampleController {
	
	@Autowired
	private TNPhotoDetailsService docService;

	@Autowired
	private EmployeeDetailsRepository repository;
	
	@Autowired
	private Tracer tracer ;
	
	@Value("${com.topnotch.properties.gatewayservice.host}")
	private String GATEWAY_HOST ;
	
	@Value("${com.topnotch.properties.gatewayservice.port}")
	private String GATEWAY_PORT ;
	
	@Value("${com.topnotch.properties.gatewayservice.transferprotocol}")
	private String TRANSFER_PROTOCOL;
	
	@GetMapping("/healthCheck")
	@ResponseBody
	public String checkLiveness(){
		
		return "App is doing fine!";
	}
	
	@GetMapping("/homePage")
	public String displayHomepage(ServerHttpRequest request, Model model) {
		
		Span span = tracer.buildSpan( "homePage" ).start();
		
		try( Scope scope = tracer.scopeManager().activate(span) ) {
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "GET" );
			Tags.HTTP_URL.set(span, request.getURI().getHost() + ":" + request.getURI().getPort() + request.getURI().getPath() );
			
			String authHeader = request.getHeaders().getFirst("Authorization");
			String userId = request.getHeaders().getFirst("UserId");

			if (authHeader == null || !authHeader.substring(0, 7).equals("Bearer ")) {
				
				Map<String, String> fields = new HashMap<>();
				fields.put( "Redirect URL" , TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint1" ) ;
				span.log(fields);
				
				return "redirect:" + TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint1";
			}

			EmployeeDetails employee = repository.findByEmail(userId);

			if (employee != null) {

				model.addAttribute("empDetails", employee);
			}else {
				
				System.out.println( "EMPLOYEE OBJECT IS NULL" );
			}
			return "home";
			
		} finally {
			
			span.finish();
		}
	}

	@PostMapping(value = "/uploadPage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String displaySpecialpage(ServerHttpRequest request, Model model,
			@RequestPart("fileToUpload") Flux<FilePart> uploadedFiles) throws InterruptedException {
		
		Span span = tracer.buildSpan( "post-documents" ).start();
		
		try( Scope scope = tracer.scopeManager().activate(span) ) {
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "GET" );
			Tags.HTTP_URL.set(span, request.getURI().getHost() + ":" + request.getURI().getPort() + request.getURI().getPath() );
			
			Map<String, String> fields = new HashMap<>();
			fields.put( "Redirect URL" , TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint3") ;
			span.log(fields);
			
			String userId = request.getHeaders().getFirst("UserId");

			Flux<DocUploadResponse> finalResponse = uploadedFiles.filter(file -> file != null).flatMap(file -> {
				
				return Mono.fromCallable( () -> {
					
					return docService.uploadDocument(userId, file);
				}).subscribeOn(Schedulers.boundedElastic());
			});
			
			model.addAttribute("files", finalResponse);
			
			return "redirect:" + TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint3" ;
			
		} finally {
			
			span.finish();
		}
	}
}
