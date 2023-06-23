/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.services;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Margin;
import com.binance.connector.client.impl.spot.Wallet;
import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.repositories.BinanceAccountRepository;

/**
 * Service de gestion des comptes utilisateurs du Bot créés par l'app mobile
 *
 * @author oandrade
 */
@Service
//rdu : implementation de UserDetailsService
public class BinanceService {
	@Autowired
	BinanceAccountRepository binanceAccountRepository;
	

	
	public void displayAssets (BinanceAccount binanceAccount) {
		SpotClient client = new SpotClientImpl(binanceAccount.getBinanceApiKey(),binanceAccount.getBinanceApiSecret());
		Wallet wallet = client.createWallet();
		Margin margin = client.createMargin();
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("needBtcValuation", Boolean.TRUE);
		System.out.println(wallet.getUserAsset(parameters));
		System.out.println(margin.allAssets());
		
	}
		


}
