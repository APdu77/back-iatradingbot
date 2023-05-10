/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.services;

import com.oas.iatradingbot.model.Config;
import com.oas.iatradingbot.repositories.ConfigRepository;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //traceOn utilisé pour récupérer afficher des traces supplémentaires
        configOptional = configRepository.findById("traceOn");
        if(configOptional.isEmpty()){
            config = new Config("traceOn", Boolean.TRUE);
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
