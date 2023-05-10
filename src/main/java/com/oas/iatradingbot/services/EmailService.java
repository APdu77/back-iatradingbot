package com.oas.iatradingbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oas.iatradingbot.repositories.BinanceAccountRepository;

@Service
public class EmailService {
	@Autowired
	BinanceAccountRepository binanceAccountRepository;
	
	JavaMailSender emailSender;
	
	public EmailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	public void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("postmaster@olivierandrade.fr");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		this.emailSender.send(message);
	}
	
}
