/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oas.iatradingbot.model.Config;
import com.oas.iatradingbot.repositories.ConfigRepository;

import jakarta.annotation.PostConstruct;

/**
 *
 * @author oandrade
 */
@Service
public class ConfigService {
    @Autowired
    ConfigRepository configRepository;

    @PostConstruct
    private void init(){
        //on va voir si les config existent sinon on les crée à la volée...
        Optional<Config> configOptional;
        Config config;

        configOptional = configRepository.findById("sponsorShipLink");
        if(configOptional.isEmpty()){
            config = new Config("sponsorShipLink", "https://accounts.binance.com/register?ref=385030280");
            configRepository.save(config);
        }
        configOptional = configRepository.findById("apiTutorial");
        if(configOptional.isEmpty()){
            config = new Config("apiTutorial", "https://www.binance.com/fr/support/faq/how-to-create-api-360002502072");
            configRepository.save(config);
        }
        configOptional = configRepository.findById("cryptoEcolo");
        if(configOptional.isEmpty()){
            config = new Config("cryptoEcolo", "https://www.cryptoecolo.fr/");
            configRepository.save(config);
        }
    }

    public String getStringConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getStringValue();
    }

    public Integer getIntegerConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getIntegerValue();
    }
    public Long getLongConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getLongValue();
    }
    public Float getFloatConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getFloatValue();
    }
    public Instant getInstantConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getInstantValue();
    }

    public Double getDoubleConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getDoubleValue();
    }

    public Boolean getBooleanConfig(String key){
        Optional<Config> result = configRepository.findById(key);
        return result.get().getBooleanValue();
    }

}
