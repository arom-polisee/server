package com.arom.polisee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 원할한 API 요청을 위해 Security 자동 설정 제거
 * Security 작업 진행 시 위 옵션 지워주세요 !
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PoliseeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliseeApplication.class, args);
	}

}
