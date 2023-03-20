/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.rest;

import com.oas.iatradingbot.model.BinanceAccount;
import com.oas.iatradingbot.services.BinanceAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oandrade
 */
@RestController
@RequestMapping("/account")
public class Account {
    @Autowired
    BinanceAccountService binanceAccountService;
    /**
     * Create account
     */
    @PostMapping(path="/")
    public void createBinanceAccount(BinanceAccount binanceAccount){
        binanceAccountService.createBinanceAccount(binanceAccount);
    }
    /**
     * Try to log the user
     * @param email
     * @param password
     * @return the session token, empty if error
     */
    @PostMapping(path = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(String email, String password){
        String token = binanceAccountService.login(email, password);
        String message = token.length()==0 ? "KO" : "OK";
        return "{\"message\":\""+message+"\",\"token\":\""+token+"\"}";
    }
    /**
     * Logout the user
     */
    @PostMapping(path = "/logout")
    public void logout(String email){
        binanceAccountService.logout(email);
    }
}
