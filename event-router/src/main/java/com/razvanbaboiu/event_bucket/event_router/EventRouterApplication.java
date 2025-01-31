package com.razvanbaboiu.event_bucket.event_router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventRouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventRouterApplication.class, args);
	}

}
