package ru.skillbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SocialNetworkAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkAccountApplication.class, args);
	}

}
