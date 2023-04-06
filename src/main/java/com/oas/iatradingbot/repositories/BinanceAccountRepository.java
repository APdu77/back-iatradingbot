/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package src.main.java.com.oas.iatradingbot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import src.main.java.com.oas.iatradingbot.model.BinanceAccount;

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
