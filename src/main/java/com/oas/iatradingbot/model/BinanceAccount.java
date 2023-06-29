/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 *
 * @author oandrade
 */
@Entity
public class BinanceAccount{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	// rdu on s'assure que chaque valeur de la colonne sera unique
	@Column(unique = true)
    private String binanceApiKey;

	//rdu propriété accessible au front en écriture seule
	@JsonProperty(access = Access.WRITE_ONLY)
    @Column(unique = true)
    private String binanceApiSecret;

	//rdu propriété en lecture seule qui indique si une clé secrete est enregistrée sur le compte
	@JsonProperty(access = Access.READ_ONLY)
	private Boolean isApiSecretSet = Boolean.FALSE;

    @Column(unique = true)
    private String binanceAccountId;

    //rdu propriété en lecture seule pour le front
 	@JsonProperty(access = Access.READ_ONLY)
    private Boolean suspended = Boolean.TRUE; // suspendu par défault tant que l'on n'a pas vérifier les possibilités de l'api et que c'est bien un filleul

    @JsonIgnore
    private String suspensionReason = "Initial state";
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String previousEmail;
    // a revoir
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private Instant createInstant=Instant.now();
    @JsonIgnore
    private Instant tokenInstant;
    @JsonIgnore
    private String mailValidationKey;
    @JsonIgnore
    private Instant mailValidationKeyInstant;
    @JsonProperty(access = Access.READ_ONLY)
    private Boolean validatedMail = Boolean.FALSE;
    
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

    public Boolean getIsApiSecretSet() {
		return isApiSecretSet;
	}

	public void setIsApiSecretSet(Boolean isApiSecretSet) {
		this.isApiSecretSet = isApiSecretSet;
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

    public String getPreviousEmail() {
		return previousEmail;
	}

	public void setPreviousEmail(String previousEmail) {
		this.previousEmail = previousEmail;
	}

	public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

	public String getMailValidationKey() {
		return mailValidationKey;
	}

	public void setMailValidationKey(String mailValidationKey) {
		this.mailValidationKey = mailValidationKey;
	}
	
	public Instant getMailValidationKeyInstant() {
		return mailValidationKeyInstant;
	}

	public void setMailValidationKeyInstant(Instant mailValidationKeyInstant) {
		this.mailValidationKeyInstant = mailValidationKeyInstant;
	}

	public Boolean getValidatedMail() {
		return validatedMail;
	}

	public void setValidatedMail(Boolean validatedMail) {
		this.validatedMail = validatedMail;
	}

}
