package com.topnotch.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topnotch.demo.dtos.EmployeeDetailsDTO;
import com.topnotch.demo.dtos.LoginForm;
import com.topnotch.demo.dtos.SignUpForm;
import com.topnotch.demo.services.RegisterNewUser;
import com.topnotch.demo.utils.JWTUtil;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;

@Controller
@RequestMapping("/myapp/authService")
public class AuthenticationController {

	@Autowired
	private RegisterNewUser registry;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private StreamBridge bridge;

	@Autowired
	private Tracer tracer;

	@Value("${com.topnotch.properties.gatewayservice.host}")
	private String GATEWAY_HOST;

	@Value("${com.topnotch.properties.gatewayservice.port}")
	private String GATEWAY_PORT;

	@Value("${com.topnotch.properties.gatewayservice.transferprotocol}")
	private String TRANSFER_PROTOCOL;

	@GetMapping("/healthCheck")
	@ResponseBody
	public String checkLiveness() {

		return "App is doing fine!";
	}

	@GetMapping("/signupPage")
	public String getSignUpForm(HttpServletRequest request, Model model) {

		Span span = tracer.buildSpan("get-sign-up-page").start();

		try (Scope scope = tracer.scopeManager().activate(span)) {
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "GET");
			Tags.HTTP_URL.set(span, request.getRequestURL().toString());

			model.addAttribute("signupForm", new SignUpForm());
			
			return "signup";
		} finally {

			span.finish();
		}
	}

	@PostMapping("/signupPage")
	public String registerUser(@ModelAttribute @Valid SignUpForm signupForm, HttpServletRequest request) {

		Span span = tracer.buildSpan("post-data-on-sign-up-page").start();

		try (Scope scope = tracer.scopeManager().activate(span)) {
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "POST");
			Tags.HTTP_URL.set(span, request.getRequestURL().toString() );
			
			Map<String, String> fields = new HashMap<>();
			fields.put( "Redirect URL" , TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint2" );
			span.log(fields);
			
			registry.register(signupForm);
			
			EmployeeDetailsDTO employee = new EmployeeDetailsDTO();
			employee.setFirst_name(signupForm.getFirst_name());
			employee.setLast_name(signupForm.getLast_name());
			employee.setDepartment(signupForm.getDepartment());
			employee.setExpertise(signupForm.getExpertise());
			employee.setEmail(signupForm.getEmail());

			System.out.println("Employee FirstName ...." + employee.getFirst_name());
			System.out.println("Employee LastName ...." + employee.getLast_name());
			System.out.println("Employee Department ...." + employee.getDepartment());
			System.out.println("Employee Expertise ...." + employee.getExpertise());
			System.out.println("Employee UserName ...." + employee.getEmail());

			bridge.send("detailsExchange-out-0", employee);
			System.out.println("Photographer object sent to message broker ....");
			
			return "redirect:" + TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint2";

		} finally {

			span.finish();
		}
	}

	@GetMapping("/loginPage")
	public String authenticateUser(Model model, HttpServletRequest request) {
		
		Span span = tracer.buildSpan( "get-login-page" ).start();
		
		try( Scope scope = tracer.scopeManager().activate(span) ){
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "GET" );
			Tags.HTTP_URL.set(span, request.getRequestURL().toString() );
			
			model.addAttribute("loginForm", new LoginForm());
			
			return "login";
		} finally {
			
			span.finish();
		}
	}

	@PostMapping("/authenticate")
	public String redirectUser(HttpServletResponse response, @ModelAttribute @Valid LoginForm loginForm) {

		// This method is just a STUB
		// the object that gets posted in the login page needs a destination
		// you can extract the details from the object if you wish to
		
		System.out.println( "Processing /authenticate endpoint ..." );
		
		return "redirect:" + TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpointToken";
	}

	@GetMapping("/generateToken")
	public String generateToken(HttpServletRequest request, HttpServletResponse response) {

		Span span = tracer.buildSpan( "generate-jwt-token" ).start();
		
		try( Scope scope = tracer.scopeManager().activate(span) ) {
			
			Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER );
			Tags.HTTP_METHOD.set(span, "GET" );
			Tags.HTTP_URL.set(span, request.getRequestURL().toString() );
			
			Map<String, String> fields = new HashMap<>();
			fields.put( "Redirect URL" , TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint3") ;
			span.log(fields);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(auth.getName());

			jwtUtil.init();
			String token = jwtUtil.generateToken(auth.getName());
			String username = jwtUtil.getUsername(token);
			System.out.println("Token decoded : " + username);

			Cookie cookie = new Cookie("jwtToken", token);
			cookie.setMaxAge(-1);
			cookie.setHttpOnly(true);
			cookie.setPath("/");

			response.addCookie(cookie);
			
			return "redirect:" + TRANSFER_PROTOCOL + "://" + GATEWAY_HOST + ":" + GATEWAY_PORT + "/myapp/gateway/endpoint3";
			
		} finally {
			
			span.finish();
		}
	}
}
