/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.depositaccount.service;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.common.dto.Transaction;
import java.math.BigDecimal;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 *
 * @author azrul
 */

//Added
@Component
public class Transfer {

    private final DepositAccountService depositAccountService;


    public Transfer(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    @KafkaListener(topics = "${kafka.deposit-debit-request-topic:deposit-debit-request}")
    @SendTo
    public Transaction transact(final Transaction trx) throws InterruptedException {
        System.out.println("Ammount:" + trx.getAmount());
        
        BigDecimal ammount = new BigDecimal(trx.getAmount());
        DepositAccount from = depositAccountService
                .findByAccountNumber(trx.getFromAccountNumber()).orElseThrow();
        
        DepositAccount to = depositAccountService
                .findByAccountNumber(trx.getToAccountNumber()).orElseThrow();
        
        if (from.getBalance().compareTo(ammount)>0 && from.getBalance().compareTo(BigDecimal.ZERO)>0){
            from.setBalance(from.getBalance().subtract(ammount));
            to.setBalance(to.getBalance().add(ammount));
            trx.setFinalBalance(from.getBalance().toPlainString());
        }
        depositAccountService.save(to,from);
        
        return trx;
    }

}
