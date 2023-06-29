package com.oas.iatradingbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oas.iatradingbot.services.ConfigService;



@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/config")
public class Config {

	@Autowired ConfigService configService;



	@GetMapping(path = "/{key}"
			, produces = "text/plain"
			)
	public ResponseEntity<String> getConfig (@PathVariable("key") String key) {
		return ResponseEntity.ok(configService.getStringConfig(key));
	}
}
