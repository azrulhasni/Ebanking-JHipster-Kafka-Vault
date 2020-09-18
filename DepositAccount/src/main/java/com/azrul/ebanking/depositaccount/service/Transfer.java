/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.depositaccount.service;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.common.dto.TransactionDTO;
import com.azrul.ebanking.depositaccount.service.mapper.DepositAccountMapper;
import java.math.BigDecimal;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 *
 * @author azrul
 */
@Component
public class Transfer {

    private final DepositAccountService depositAccountService;

    private final DepositAccountMapper depositAccountMapper;

    public Transfer(DepositAccountService depositAccountService, DepositAccountMapper depositAccountMapper) {
        this.depositAccountService = depositAccountService;
        this.depositAccountMapper = depositAccountMapper;
    }

    @KafkaListener(topics = "${kafka.deposit-debit-request-topic}")
    @SendTo
    public TransactionDTO transact(final TransactionDTO trx) throws InterruptedException {
        System.out.println("Ammount:" + trx.getAmmount());
        
        BigDecimal ammount = new BigDecimal(trx.getAmmount());
        DepositAccount from = depositAccountService
                .findByAccountNumber(trx.getFromAccountNumber())
                .map(depositAccountMapper::toEntity).orElseThrow();
        
        DepositAccount to = depositAccountService
                .findByAccountNumber(trx.getToAccountNumber())
                .map(depositAccountMapper::toEntity).orElseThrow();
        
        if (from.getBalance().compareTo(ammount)>0 && from.getBalance().compareTo(BigDecimal.ZERO)>0){
            from.setBalance(from.getBalance().subtract(ammount));
            to.setBalance(to.getBalance().add(ammount));
            trx.setFinalBalance(from.getBalance().toPlainString());
        }
        depositAccountService.save(depositAccountMapper.toDto(to),depositAccountMapper.toDto(from));
        
        return trx;
    }

}
