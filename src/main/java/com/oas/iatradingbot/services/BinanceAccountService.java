/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.services;

import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.repositories.BinanceAccountRepository;
import com.oas.iatradingbot.tools.StringTool;
import jakarta.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des comptes utilisateurs du Bot créés par l'app mobile
 * @author oandrade
 */
@Service
public class BinanceAccountService {
    @Autowired
    BinanceAccountRepository binanceAccountRepository;
    @Autowired
    MessageDigest MessageDigest;
    
    @Transactional
    public void createBinanceAccount(BinanceAccount binanceAccount){
        String hexHash = StringTool.bytesToHex(MessageDigest.digest(binanceAccount.getPassword().getBytes(StandardCharsets.UTF_8)));
        binanceAccount.setPassword(hexHash);
        binanceAccountRepository.save(binanceAccount);
    }
    
    @Transactional
    public String login(String email, String password){        
        
        String token = "";
        String hexHash = StringTool.bytesToHex(MessageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
        BinanceAccount binanceAccount = binanceAccountRepository.findByEmailAndPassword(email, hexHash);
        if(binanceAccount!=null){
            //on génère une token
            token=UUID.randomUUID().toString();
            binanceAccount.setToken(token);
            binanceAccount.setTokenInstant(Instant.now());
            binanceAccountRepository.save(binanceAccount);
        }
        return token;
    }
    @Transactional
    public void logout(String email){
        BinanceAccount binanceAccount = binanceAccountRepository.findByEmail(email);
        if(binanceAccount!=null){
            binanceAccount.setToken(null);
            binanceAccount.setTokenInstant(null);
            binanceAccountRepository.save(binanceAccount);
        }
    }
}
