/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.services;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.model.ChangePassword;
import com.oas.iatradingbot.enumeration.ValidationMailType;
import com.oas.iatradingbot.repositories.BinanceAccountRepository;
import com.oas.iatradingbot.tools.StringTool;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service de gestion des comptes utilisateurs du Bot créés par l'app mobile
 *
 * @author oandrade
 */
@Service
//rdu : implementation de UserDetailsService
public class BinanceAccountService {
	@Autowired
	BinanceAccountRepository binanceAccountRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	MessageDigest messageDigest;

	@Transactional
	public BinanceAccount createBinanceAccount(BinanceAccount binanceAccountToCreate){
		BinanceAccount binanceAccountFound = binanceAccountRepository.findByEmail(binanceAccountToCreate.getEmail());
		String hexHash = StringTool.bytesToHex(
				messageDigest.digest(binanceAccountToCreate.getPassword().getBytes(StandardCharsets.UTF_8)));
		binanceAccountToCreate.setPassword(hexHash);
		// redondance??
		//binanceAccountToCreate.setEmail(binanceAccountToCreate.getEmail());
		String validationMailKey = UUID.randomUUID().toString();
		binanceAccountToCreate.setMailValidationKey(validationMailKey);
		binanceAccountToCreate.setMailValidationKeyInstant(Instant.now());
		if (binanceAccountFound != null && binanceAccountFound.getValidatedMail()==false) {
			binanceAccountRepository.deleteById(binanceAccountFound.getId());
			binanceAccountRepository.flush();
		}
		
		try {
			BinanceAccount binanceAccountCreated = this.binanceAccountRepository.save(binanceAccountToCreate);
			this.emailService.sendMessage(binanceAccountCreated, ValidationMailType.CREATION);
			return binanceAccountCreated ;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Transactional
	public BinanceAccount validateBinanceAccount(String keyToCheck)throws Exception{
		Optional<BinanceAccount> binanceAccount = binanceAccountRepository.findByMailValidationKey(keyToCheck);
		//Instant mailValidationKeyInstant =  binanceAccount.get().getMailValidationKeyInstant();

		if( binanceAccount.isPresent() &&
				Duration.between(binanceAccount.get().getMailValidationKeyInstant(), Instant.now()).getSeconds() <= 3600 ) {
			binanceAccount.get().setValidatedMail(true);
			binanceAccount.get().setMailValidationKey(null);
			binanceAccount.get().setMailValidationKeyInstant(null);
			return binanceAccountRepository.save(binanceAccount.get());
		} else if (binanceAccount.isEmpty()) {
			throw new EntityNotFoundException("Code de vérification incorrect !!");
		} else {
			throw new ArithmeticException("Votre code de vérification a expiré !");
		}
		// redondance??
		//binanceAccountToCreate.setEmail(binanceAccountToCreate.getEmail());
	}
	

	@Transactional
	public BinanceAccount updateBinanceAccount(BinanceAccount binanceAccountToUpdate) {

		BinanceAccount binanceAccount = this.binanceAccountRepository.findById(binanceAccountToUpdate.getId()).get();

		if (binanceAccountToUpdate.getBinanceApiKey() != null &&
			!binanceAccountToUpdate.getBinanceApiKey().equals(binanceAccount.getBinanceApiKey())) {
			binanceAccount.setBinanceApiKey(binanceAccountToUpdate.getBinanceApiKey());
		}
		
		else if (binanceAccountToUpdate.getBinanceApiSecret() != null &&
			!binanceAccountToUpdate.getBinanceApiSecret().equals(binanceAccount.getBinanceApiSecret())) {
			binanceAccount.setBinanceApiSecret(binanceAccountToUpdate.getBinanceApiSecret());
			binanceAccount.setIsApiSecretSet(true);
		}
		
		else if (binanceAccountToUpdate.getBinanceAccountId() != null &&
			!binanceAccountToUpdate.getBinanceAccountId().equals(binanceAccount.getBinanceAccountId())) {
			binanceAccount.setBinanceAccountId(binanceAccountToUpdate.getBinanceAccountId());
		}

		return this.binanceAccountRepository.save(binanceAccount);
	}

	@Transactional
	public String login(String loginToken) {

		String token = "";
		JSONObject jsonLoginToken = new JSONObject(loginToken);
		String email = jsonLoginToken.get("email").toString();
		String password = jsonLoginToken.get("password").toString();
		String hexHash = StringTool.bytesToHex(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
		BinanceAccount binanceAccount = binanceAccountRepository.findByEmailAndPassword(email, hexHash);
		if (binanceAccount != null && binanceAccount.getValidatedMail()==true) {
			// on génère une token
			token = UUID.randomUUID().toString();
			binanceAccount.setToken(token);
			binanceAccount.setTokenInstant(Instant.now());
			binanceAccountRepository.save(binanceAccount);
		}
		return token;
	}

	@Transactional
	public BinanceAccount findBinanceAccount(Long id) {
		Optional<BinanceAccount> binanceAccount = binanceAccountRepository.findById(id);
		return binanceAccount.get();
	}

	@Transactional
	// rdu : logout via token au lieu de email
	public void logout(String token) {
		BinanceAccount binanceAccount = binanceAccountRepository.findByToken(token);
		if (binanceAccount != null) {
			binanceAccount.setToken(null);
			binanceAccount.setTokenInstant(null);
			binanceAccountRepository.save(binanceAccount);
		}
	}

	@Transactional
	public String changePassword(ChangePassword passwords, String token) {
		System.out.println(passwords);
		// System.out.println(passwords);
		String cause = "";
		String password = passwords.getPassword();
		String newPassword = passwords.getNewPassword();
		String confirmPassword = passwords.getConfirmPassword();
		String hexHash = StringTool.bytesToHex(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
		String newHexHash = StringTool.bytesToHex(messageDigest.digest(newPassword.getBytes(StandardCharsets.UTF_8)));

		BinanceAccount binanceAccount = this.binanceAccountRepository.findByToken(token);

		if (!hexHash.equals(binanceAccount.getPassword())) {
			cause = "wrong password";
		} else if (password.equals(newPassword) || !newPassword.equals(confirmPassword)) {
			cause = "unchanged";
		} else if (!newPassword.equals(confirmPassword)) {
			cause = "not matching";
		} else
			binanceAccount.setPassword(newHexHash);
			binanceAccountRepository.save(binanceAccount);

		return cause;
	}
	
	public BinanceAccount changeEmail(BinanceAccount newBinanceAccount) {
		//Account reellement en base car requete sur l'id
		BinanceAccount binanceAccountFound = binanceAccountRepository.findById(newBinanceAccount.getId()).get();
		//on checke si le nouvel email est assiocie a un BinanceAcount en base
		BinanceAccount binanceAccount = binanceAccountRepository.findByEmail(newBinanceAccount.getEmail());
		
		
		
		if (binanceAccount==null) {
			String validationMailKey = UUID.randomUUID().toString();
			binanceAccountFound.setMailValidationKey(validationMailKey);
			binanceAccountFound.setMailValidationKeyInstant(Instant.now());
			binanceAccountFound.setPreviousEmail(binanceAccountFound.getEmail());
			binanceAccountFound.setEmail(newBinanceAccount.getEmail());
		} else if (binanceAccount!=null && binanceAccount.getValidatedMail()==true) {
			throw new EntityExistsException("Un compte associé à cet email existe déjà !");
		}else if (binanceAccount!=null && binanceAccount.getValidatedMail()==false) {
			throw new EntityExistsException("Un compte associé à cet email est en cours de création !");
		}
		
		
		
		return binanceAccountRepository.save(binanceAccountFound);
		
	}

	public Boolean checkHeader(String token) {
		BinanceAccount binanceAccount = binanceAccountRepository.findByToken(token);
		// if(binanceAccount != null){
		// return true;
		// }
		// return false;
		return binanceAccount != null;
	}
	

}
