/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.transaction.web.rest;


import com.azrul.ebanking.common.dto.Transaction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

@RestController
@RequestMapping("/api/transaction-kafka")
public class TransactionKafkaResource {

    @Value("${kafka.deposit-debit-request-topic:deposit-debit-request}")
    private String depositDebitRequestTopic;

    @Value("${kafka.deposit-debit-response-topic:deposit-debit-response}")
    private String depositDebitResponseTopic;
    
    @Autowired
    ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    
    @Autowired
    VaultTemplate vaultTemplate;

    private final Logger log = LoggerFactory.getLogger(TransactionKafkaResource.class);

    public TransactionKafkaResource() {
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) throws ExecutionException, InterruptedException {
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", depositDebitRequestTopic, "AMOUNT", transaction);
        String encryptedStr = encrypt(transaction);
        ProducerRecord<String, String> record = new ProducerRecord<>(depositDebitRequestTopic,"AMOUNT",encryptedStr);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, depositDebitResponseTopic.getBytes()));
        
        // post in kafka topic
        RequestReplyFuture<String,String,String> sendAndReceive = kafkaTemplate.sendAndReceive(record,Duration.ofSeconds(3600));
        
        // get consumer record
        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
        // return consumer value
        String encryptedReplyStr = consumerRecord.value();
        return (Transaction) decrypt(encryptedReplyStr);
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
            log.error(null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                log.error(null, ex);
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
            log.error(null, ex);
        } catch (ClassNotFoundException ex) {
            log.error(null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                log.error(null, ex);
            }
        }
        return null;
    }
}
