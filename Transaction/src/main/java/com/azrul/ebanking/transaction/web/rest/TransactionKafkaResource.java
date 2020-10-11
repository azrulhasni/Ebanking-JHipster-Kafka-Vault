/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.transaction.web.rest;


//import com.azrul.ebanking.transaction.config.KafkaProperties;
import com.azrul.ebanking.common.dto.Transaction;
import java.net.URI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/transaction-kafka")
public class TransactionKafkaResource {

    @Value("${kafka.deposit-debit-request-topic:deposit-debit-request}")
    private String depositDebitRequestTopic;

    @Value("${kafka.deposit-debit-response-topic:deposit-debit-response}")
    private String depositDebitResponseTopic;
    
    @Autowired
    ReplyingKafkaTemplate<String, Transaction, Transaction> kafkaTemplate;
    
    @Autowired
    VaultTemplate vaultTemplate;
    //private WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(TransactionKafkaResource.class);

    public TransactionKafkaResource() {
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) throws ExecutionException, InterruptedException {
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", depositDebitRequestTopic, "AMOUNT", transaction);
        //kafkaTemplate.setDefaultReplyTimeout(Duration.ofHours(1));
        ProducerRecord<String, Transaction> record = new ProducerRecord<>(depositDebitRequestTopic,"AMOUNT",transaction);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, depositDebitResponseTopic.getBytes()));
        
        // post in kafka topic
        RequestReplyFuture<String, Transaction, Transaction> sendAndReceive = kafkaTemplate.sendAndReceive(record,Duration.ofHours(1));
        
        // get consumer record
        ConsumerRecord<String, Transaction> consumerRecord = sendAndReceive.get();
        // return consumer value
        return consumerRecord.value();
    }
    
    @PostMapping("/encrypt")
    public String encrypt(){
//        URI baseUrl = URI.create("https://127.0.0.1:8200");
//        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(baseUrl), 
//            new TokenAuthentication("s.6HAohs85JhXqRlA2aHqLZPpx"));
        VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
        String encrypted = transitOperations.encrypt("my-encryption-key", "Hello World");
        String plain = transitOperations.decrypt("my-encryption-key", encrypted);
        return plain;
        /*VaultTransitPlain plain = new VaultTransitPlain();
        plain.setPlaintext("Sm9uLFNub3csNDExMSAxMTExIDExMTEgMTExMSxyZXN0YXVyYW50LCwxODkyMDMwOTAzCg==");
        
        VaultTransitEncrypted encrypted = webClient.post()
        .uri(baseUrl)
        .header("X-Vault-Token","s.6HAohs85JhXqRlA2aHqLZPpx")
        .body(BodyInserters.fromValue(plain))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()        
        .bodyToMono(VaultTransitEncrypted.class).single().block();
        
        System.out.println(encrypted.getCiphertext());*/
        
    }

    
}
