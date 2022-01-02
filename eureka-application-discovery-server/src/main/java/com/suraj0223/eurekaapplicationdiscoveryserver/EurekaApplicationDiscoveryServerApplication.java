package com.suraj0223.eurekaapplicationdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplicationDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplicationDiscoveryServerApplication.class, args);
	}

}
