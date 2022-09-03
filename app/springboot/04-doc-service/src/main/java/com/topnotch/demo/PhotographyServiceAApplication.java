package com.topnotch.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.opentracing.Tracer;

@SpringBootApplication
//@EnableDiscoveryClient
public class PhotographyServiceAApplication {

	@Bean
	public Tracer initTracer() {
		
		SamplerConfiguration sampler = new SamplerConfiguration().withType("const").withParam(1);
		ReporterConfiguration reporter = new ReporterConfiguration().withLogSpans(true);
		Configuration config = new Configuration("doc-service").withSampler(sampler).withReporter(reporter) ;
		
		return config.getTracer();
	}

	public static void main(String[] args) {
		SpringApplication.run(PhotographyServiceAApplication.class, args);
	}

}
