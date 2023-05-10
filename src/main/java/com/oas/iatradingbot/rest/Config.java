package com.oas.iatradingbot.rest;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 

@RestController
@RequestMapping("/config")
public class Config {

	HashMap<String, String> configs = new HashMap<>();
	
	public Config() {
		this.configs.put("sponsorShipLink", "https://accounts.binance.com/register?ref=385030280");
		this.configs.put("apiTutorial", "https://www.binance.com/fr/support/faq/how-to-create-api-360002502072");
		this.configs.put("cryptoEcolo", "https://www.cryptoecolo.fr/");
	}
	
	@GetMapping(path = "/{key}")
	public String getConfig (@PathVariable("key") String key) {
		return configs.get(key);
	}
	
}
