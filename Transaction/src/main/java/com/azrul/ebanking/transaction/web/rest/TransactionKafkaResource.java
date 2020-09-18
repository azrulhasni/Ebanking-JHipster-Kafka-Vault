package com.azrul.ebanking.transaction.web.rest;

//import com.azrul.ebanking.transaction.config.KafkaProperties;
import com.azrul.ebanking.common.dto.TransactionDTO;
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
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;

@RestController
@RequestMapping("/api/transaction-kafka")
public class TransactionKafkaResource {

    @Value("${kafka.deposit-debit-request-topic}")
    private String depositDebitRequestTopic;

    @Value("${kafka.deposit-debit-response-topic}")
    private String depositDebitResponseTopic;
    
    @Autowired
    ReplyingKafkaTemplate<String, TransactionDTO, TransactionDTO> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(TransactionKafkaResource.class);

    public TransactionKafkaResource() {
    }

    @PostMapping("/transfer")
    public TransactionDTO transfer(@RequestBody TransactionDTO transactionDTO) throws ExecutionException, InterruptedException {
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", depositDebitRequestTopic, "AMMOUNT", transactionDTO);

        ProducerRecord<String, TransactionDTO> record = new ProducerRecord<>(depositDebitRequestTopic,"AMMOUNT",transactionDTO);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, depositDebitResponseTopic.getBytes()));
        
        // post in kafka topic
        RequestReplyFuture<String, TransactionDTO, TransactionDTO> sendAndReceive = kafkaTemplate.sendAndReceive(record,Duration.ofSeconds(10));

        // get consumer record
        ConsumerRecord<String, TransactionDTO> consumerRecord = sendAndReceive.get();
        // return consumer value
        return consumerRecord.value();
    }

    
}
