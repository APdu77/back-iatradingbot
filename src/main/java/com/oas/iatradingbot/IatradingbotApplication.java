package com.oas.iatradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class IatradingbotApplication {

	public static void main(String[] args) {
            ApplicationContext ctx = SpringApplication.run(IatradingbotApplication.class, args);
	}
}
