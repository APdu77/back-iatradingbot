package com.oas.iatradingbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oas.iatradingbot.enumeration.ValidationMailType;
import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.repositories.BinanceAccountRepository;

@Service
public class EmailService {

	@Autowired
	BinanceAccountRepository binanceAccountRepository;

	JavaMailSender emailSender;

	public EmailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendMessage(BinanceAccount binanceAccount, ValidationMailType type) {
		SimpleMailMessage message = new SimpleMailMessage();
		String subject = "";
		String text = "";
		String creationText = "\nVotre code est valable 60 minutes. Veuillez l'utiliser avant son expiration."
				+ "\nSi vous n’êtes pas à l’origine de cette action, veuillez ignorer ce mail.";
		String modificationText = "\nVotre code est valable 5 minutes. Veuillez l'utiliser avant son expiration."
				+ "\nSi vous n’êtes pas à l’origine de cette action, veuillez ignorer ce mail.";
		String to = binanceAccount.getEmail() ;
		String key = binanceAccount.getMailValidationKey() ;
		
		//message personalise selon le type de demande (creation compte ou modification email)
		if (type == ValidationMailType.CREATION) {
			subject = "Création de votre compte CryptoEcoloApp";
			text = "Bienvenue sur CryptoEcoloApp, votre code de verification est: " + key + " ."+ creationText;
		} else {
			subject = "Changement de votre adresse mail CryptoEcoloApp";
			text = "Une demande de modification de votre adresse mail a été effectuée sur votre compte, votre code de verification est: "
				+ key + " ."+ modificationText;
		}	
		message.setFrom("postmaster@olivierandrade.fr");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		try {
			this.emailSender.send(message);
			
		} catch (Exception e) {
			System.out.println(e);// TODO: handle exception
		}
	}

}
