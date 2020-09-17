/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.depositaccount.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 *
 * @author azrul
 */


@Component
public class Debit {
        private BigDecimal balance = new BigDecimal("1000");
        
  
	 
	 @KafkaListener(topics = "${kafka.deposit-debit-request-topic}")
	 @SendTo
	  public String listen(String ammount) throws InterruptedException {
                System.out.println("Ammount:"+ammount);
                BigDecimal a = new BigDecimal(ammount);
                BigDecimal newBalance = balance.subtract(a);
                balance = newBalance;
                return newBalance.toPlainString();
	  }

}
