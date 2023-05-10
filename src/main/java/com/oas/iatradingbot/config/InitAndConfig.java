/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oas.iatradingbot.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.internal.ws.RealWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author oandrade
 */
@Configuration
public class InitAndConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
	        .addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("OPTIONS","GET", "POST", "PUT", "PATCH", "DELETE")
	        .allowedHeaders(
	        		//"Content-Type","Authorization"
	        		"*"
	        		);
    }
    
    @Bean
    public MessageDigest messageDigest(){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
        }
        return md;
    }
    
    
    
}
