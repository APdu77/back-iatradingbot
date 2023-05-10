/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.rest;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.model.ChangePassword;
import com.oas.iatradingbot.services.BinanceAccountService;

/**
 *
 * @author oandrade
 */
@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/account")
public class Account {
	@Autowired
	BinanceAccountService binanceAccountService;

	/**
	 * Get account info
	 */
	@GetMapping(path = "/{id}")
	public BinanceAccount findBinanceAccount(@PathVariable("id") Long id, @RequestHeader("authorization") String token)
			throws Exception {
		// todo verif token dans le header
		System.out.println(token);
		if (binanceAccountService.checkHeader(token)) {
			return this.binanceAccountService.findBinanceAccount(id);
		}
		throw new EntityNotFoundException("Informations inaccessibles");
	}

	/**
	 * Create account
	 */
	@PostMapping(path = "/")
	public BinanceAccount createBinanceAccount(@RequestBody BinanceAccount binanceAccountToCreate) {
		return this.binanceAccountService.createBinanceAccount(binanceAccountToCreate);
	}

	/**
	 * Update account info
	 */
	@PutMapping(path = "/")
	public BinanceAccount updateBinanceAccount(@RequestBody BinanceAccount binanceAccountToUpdate,
			@RequestHeader("authorization") String token) throws Exception {
		System.out.println(token);
		if (binanceAccountService.checkHeader(token)) {
			return this.binanceAccountService.updateBinanceAccount(binanceAccountToUpdate);
		}
		throw new EntityNotFoundException("Informations innaccessibles");
	}

	@PostMapping(path = "/password"
			//, consumes = { "text/plain;charset=UTF-8","application/json" }
			//, headers = "Content-Type=text/plain;charset=UTF-8"
					//, produces = MediaType.APPLICATION_JSON_VALUE
					)
	public Object changepassword(@RequestBody ChangePassword passwords,
			// public Object changepassword(@RequestBody JSONObject passwords,
			@RequestHeader("authorization") String token) throws Exception {
		// System.out.println(passwords.getPassword());
		// System.out.println(token);
		// JSONObject json = new JSONObject(passwords.toString());
		// System.out.println(json);
		// .out.println(json.get("password"));
		if (binanceAccountService.checkHeader(token)) {
			String cause = this.binanceAccountService.changePassword(passwords, token);
			String message = cause.length() == 0 ? "OK" : "KO";
			System.out.println(cause);
			return "{\"message\":\"" + message + "\",\"cause\":\"" + cause + "\"}";

		}
		throw new EntityNotFoundException("Modifications impossibles");
	}

	/**
	 * Try to log the user
	 * 
	 * @param email
	 * @param password
	 * @return the session token, empty if error
	 */
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	// rajout de @RequestBody et remplacement des 2 string en param par un objet
	// BinanceAcoount
	public Object login(@RequestBody String loginToken) {
		System.out.println(loginToken);
		String token = binanceAccountService.login(loginToken);
		String message = token.length() == 0 ? "KO" : "OK";
		// peut-etre rajouter id dans la reponse
		return "{\"message\":\"" + message + "\",\"token\":\"" + token + "\"}";
	}

	/**
	 * Logout the user
	 */
	@PostMapping(path = "/logout")
	// rdu : logout via token au lieu de email
	public void logout(@RequestBody String token) {
		System.out.println(token);
		binanceAccountService.logout(token);
	}

}
