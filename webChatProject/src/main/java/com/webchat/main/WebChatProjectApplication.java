package com.webchat.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class WebChatProjectApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(WebChatProjectApplication.class, args);
	}

}
