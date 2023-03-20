/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;


/**
 *
 * @author oandrade
 */
@Entity
public class BinanceAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String binanceApiKey;
    @JsonIgnore
    private String binanceApiSecret;
    private String binanceAccountId;
    @JsonIgnore
    private Boolean suspended = Boolean.TRUE; // suspendu par défault tant que l'on n'a pas vérifier les possibilités de l'api et que c'est bien un filleul    
    @JsonIgnore
    private String suspensionReason = "Initial state"; 
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private Instant createInstant=Instant.now();
    @JsonIgnore
    private Instant tokenInstant;
    
    public BinanceAccount(){}

    public BinanceAccount(String binanceApiKey, String binanceApiSecret) {
        this.binanceApiKey = binanceApiKey;
        this.binanceApiSecret = binanceApiSecret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getBinanceApiKey() {
        return binanceApiKey;
    }

    public void setBinanceApiKey(String binanceApiKey) {
        this.binanceApiKey = binanceApiKey;
    }

    public String getBinanceApiSecret() {
        return binanceApiSecret;
    }

    public void setBinanceApiSecret(String binanceApiSecret) {
        this.binanceApiSecret = binanceApiSecret;
    }

    public String getBinanceAccountId() {
        return binanceAccountId;
    }

    public void setBinanceAccountId(String binanceAccountId) {
        this.binanceAccountId = binanceAccountId;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getSuspensionReason() {
        return suspensionReason;
    }

    public void setSuspensionReason(String suspensionReason) {
        this.suspensionReason = suspensionReason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreateInstant() {
        return createInstant;
    }

    public void setCreateInstant(Instant createInstant) {
        this.createInstant = createInstant;
    }

    public Instant getTokenInstant() {
        return tokenInstant;
    }

    public void setTokenInstant(Instant tokenInstant) {
        this.tokenInstant = tokenInstant;
    }
    
    

}