/*
 * package com.topnotch.demo.utils;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.cloud.client.ServiceInstance; import
 * org.springframework.cloud.client.discovery.DiscoveryClient; import
 * org.springframework.stereotype.Service;
 * 
 * @Service public class DiscoveryUtil {
 * 
 * @Autowired private DiscoveryClient discClient;
 * 
 * @Value("${topnotch.eureka.current.host}") private String currentHost;
 * 
 * public String getServiceUri(String serviceName) {
 * 
 * List<ServiceInstance> info = discClient.getInstances(serviceName);
 * 
 * String serviceURI = info.get(0).getUri().toString();
 * 
 * if (currentHost.equals("localhost") || currentHost.equals("127.0.0.1")) {
 * 
 * System.out.println("DISCOVERY UTIL IS WORKING : " + currentHost); return
 * serviceURI; }
 * 
 * String[] URI_components = serviceURI.split(":");
 * 
 * String http_or_https = URI_components[0];
 * 
 * String port = URI_components[2];
 * 
 * String newServiceURI = http_or_https + ":" + "//" + currentHost + ":" + port;
 * 
 * return newServiceURI; } }
 */