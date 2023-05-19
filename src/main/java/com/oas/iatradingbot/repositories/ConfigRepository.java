/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.oas.iatradingbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oas.iatradingbot.model.Config;

/**
 *
 * @author oandrade
 */
public interface ConfigRepository extends JpaRepository<Config,String> {

}
