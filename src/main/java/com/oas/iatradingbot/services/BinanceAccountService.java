/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.oas.iatradingbot.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import src.main.java.com.oas.iatradingbot.model.BinanceAccount;
import src.main.java.com.oas.iatradingbot.repositories.BinanceAccountRepository;
import src.main.java.com.oas.iatradingbot.tools.StringTool;


/**
 * Service de gestion des comptes utilisateurs du Bot créés par l'app mobile
 * @author oandrade
 */
@Service
//rdu : implementation de UserDetailsService
public class BinanceAccountService {
    @Autowired
    BinanceAccountRepository binanceAccountRepository;
    @Autowired
    MessageDigest messageDigest;
    
    @Transactional
    public void createBinanceAccount(BinanceAccount binanceAccount){
        String hexHash = StringTool.bytesToHex(messageDigest.digest(binanceAccount.getPassword().getBytes(StandardCharsets.UTF_8)));
        binanceAccount.setPassword(hexHash);
        binanceAccountRepository.save(binanceAccount);
    }
    
    @Transactional
    public List<String> login(String email, String password){        
        
        String token = "";
        String cause = "unregistered";
        List<String> table = new ArrayList<>();
        String hexHash = StringTool.bytesToHex(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
        BinanceAccount binanceAccountExists = binanceAccountRepository.findByEmail(email);
        BinanceAccount binanceAccount = binanceAccountRepository.findByEmailAndPassword(email, hexHash);
        
        if (binanceAccountExists!=null) {
        	cause = "password";

	        if( binanceAccount!=null ){
	            //on génère une token
	            token=UUID.randomUUID().toString();
	            binanceAccount.setToken(token);
	            binanceAccount.setTokenInstant(Instant.now());
	            binanceAccountRepository.save(binanceAccount);
	            cause="";
	        }
        }
        table.add(token);
        table.add(cause);
        return table;
    }
    
    @Transactional
    //rdu : logout via token au lieu de email
    public void logout(String token){
        BinanceAccount binanceAccount = binanceAccountRepository.findByToken(token);
        if(binanceAccount!=null){
            binanceAccount.setToken(null);
            binanceAccount.setTokenInstant(null);
            binanceAccountRepository.save(binanceAccount);
            System.out.println(binanceAccount.getToken());
        }
    }

    
}
