package com.oas.iatradingbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.oas.iatradingbot.repositories.BinanceAccountRepository;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class IatradingbotApplication {

	public static void main(String[] args) {
            ApplicationContext ctx = SpringApplication.run(IatradingbotApplication.class, args);
	}
	
	@Autowired
	BinanceAccountRepository binanceAccountRepository;
	
	public void run(String... args) throws Exception {
	System.out.println("*****************"+binanceAccountRepository.findAllExpirated());
	}
}
