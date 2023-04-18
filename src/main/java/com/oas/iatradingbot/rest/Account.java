/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.oas.iatradingbot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.com.oas.iatradingbot.model.BinanceAccount;
import src.main.java.com.oas.iatradingbot.services.BinanceAccountService;

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
    @GetMapping(path="/{id}")
    public BinanceAccount findBinanceAccount(@RequestParam Long id){
    	//todo verif token dans le header
    	return this.binanceAccountService.findBinanceAccount(id);
    }
    /**
     * Create account
     */
    @PostMapping(path="/")
    public BinanceAccount createBinanceAccount(BinanceAccount binanceAccountToCreate){
        return this.binanceAccountService.createBinanceAccount(binanceAccountToCreate);
    }
    /**
    * Update account info
    */
   @PutMapping(path="/")
   public BinanceAccount updateBinanceAccount(@RequestBody BinanceAccount binanceAccountToUpdate){
   	//todo verif token dans le header
   	return binanceAccountToUpdate;
   }
    
    /**
     * Try to log the user
     * @param email
     * @param password
     * @return the session token, empty if error
     */
    @PostMapping(path = "/login"
    		,produces = MediaType.APPLICATION_JSON_VALUE
    		)
    //rajout de @RequestBody et remplacement des 2 string en param par un objet BinanceAcoount
    public String login(@RequestBody List<String> emailAndPassword){
    	System.out.println(emailAndPassword);
    	System.out.println(emailAndPassword.get(1));
    	String token = binanceAccountService.login(emailAndPassword.get(0), emailAndPassword.get(1));
        String message = token.length()==0 ? "KO" : "OK";
        //peut-etre rajouter id dans la reponse
        return "{\"message\":\""+message+"\",\"token\":\""+token+"\"}";
    }
    /**
     * Logout the user
     */
    @PostMapping(path = "/logout")
    //rdu : logout via token au lieu de email
    public void logout(@RequestBody String token){
    	System.out.println(token);
        binanceAccountService.logout(token);
    }
    
}
