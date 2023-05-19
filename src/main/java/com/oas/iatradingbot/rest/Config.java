package com.oas.iatradingbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oas.iatradingbot.services.ConfigService;



@RestController
@RequestMapping("/config")
public class Config {

	@Autowired ConfigService configService;



	@GetMapping(path = "/{key}")
	public String getConfig (@PathVariable("key") String key) {
		return configService.getStringConfig(key);
	}
}
