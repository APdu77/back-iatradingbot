/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.oas.iatradingbot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oas.iatradingbot.model.BinanceAccount;

/**
 *
 * @author oandrade
 */
public interface BinanceAccountRepository extends JpaRepository<BinanceAccount,Long>{
    public BinanceAccount findByEmailAndPassword(String email, String password);
    public BinanceAccount findByEmail(String email);
    public BinanceAccount findByBinanceApiKey(String binanceApiKey);
    public BinanceAccount findByToken(String token);
    public Optional<BinanceAccount> findByMailValidationKey(String mailValidationKey);
    
    // Requete pour retrouver la liste des BinanceAcount non validés et 
    // dont le mailValidationKey a expiré (> 60 minutes)
 	@Query("FROM BinanceAccount b " + "WHERE b.validatedMail = false "
 	+ "AND CURRENT_TIMESTAMP - b.mailValidationKeyInstant > 3600000")
 	List<BinanceAccount> findAllExpirated();
 	
 	@Query("FROM BinanceAccount b " + "WHERE b.validatedMail = false "
 		 	+ "AND CURRENT_TIMESTAMP - b.mailValidationKeyInstant < 3600000")
 		 	List<BinanceAccount> findAllNotExpirated();
 	
 	//@Query("FROM BinanceAccount b " + "WHERE b.validatedMail = false "
 	//	 	+ "AND b.mailValidationKeyInstant > DATE_SUB(CURRENT_TIMESTAMP,INTERVAL 1 HOUR) ")
 	//	 	List<BinanceAccount> findAllExpirated2();

}
