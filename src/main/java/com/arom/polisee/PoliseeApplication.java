package com.arom.polisee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)  // 🔥 Security 자동 설정 제거
public class PoliseeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliseeApplication.class, args);
	}

}
