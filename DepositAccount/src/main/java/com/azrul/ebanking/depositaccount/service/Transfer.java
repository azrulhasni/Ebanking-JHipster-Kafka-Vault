/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.depositaccount.service;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.common.dto.Transaction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

/**
 *
 * @author azrul
 */
@Component
public class Transfer {
   
    @Autowired
    VaultTemplate vaultTemplate;
    
    @Autowired
    DepositAccountService depositAccountService;

    public Transfer() {
    }
    
    private final org.slf4j.Logger log = LoggerFactory.getLogger(Transfer.class);


    @KafkaListener(topics = "${kafka.deposit-debit-request-topic:deposit-debit-request}")
    @SendTo
    public String transact(final String data) throws InterruptedException {
        Transaction trx = (Transaction)decrypt(data);
        Logger.getLogger(Transfer.class.getName()).log(Level.SEVERE, null, "Ammount:" + trx.getAmount());

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
        log.info("TRX.finalBalance:"+trx.getFinalBalance());
        log.info("TRX.from:"+trx.getFromAccountNumber());
        log.info("TRX.to:"+trx.getToAccountNumber());
        log.info("TRX.ammount:"+trx.getAmount());
        return encrypt(trx);
    }
    
    public String encrypt(Object obj){
        ObjectOutputStream os = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
            byte[] dataBytes =  out.toByteArray();
            String dataStr = Base64.getEncoder().encodeToString(dataBytes);
            VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
            String encryptedDataStr = transitOperations.encrypt("my-encryption-key", dataStr);
            return encryptedDataStr;
        } catch (IOException ex) {
            log.info(null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                log.info(null, ex);
            }
        }
        return null;
    }
    public Object decrypt(String encryptedDataStr)  {
        ObjectInputStream is = null;
        try {
            VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
            String decryptedDataStr = transitOperations.decrypt("my-encryption-key", encryptedDataStr);
            byte[] data = Base64.getDecoder().decode(decryptedDataStr);
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException ex) {
            log.info(null, ex);
        } catch (ClassNotFoundException ex) {
            log.info(null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                log.info(null, ex);
            }
        }
        return null;
    }
}
