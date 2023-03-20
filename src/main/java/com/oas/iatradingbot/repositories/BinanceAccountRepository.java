/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.oas.iatradingbot.repositories;

import com.oas.iatradingbot.model.BinanceAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author oandrade
 */
public interface BinanceAccountRepository extends JpaRepository<BinanceAccount,Long>{
    public BinanceAccount findByEmailAndPassword(String email, String password);
    public BinanceAccount findByEmail(String email);
    public BinanceAccount findByBinanceApiKey(String binanceApiKey);
    public BinanceAccount findByToken(String token);
    
}
