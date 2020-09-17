package com.azrul.ebanking.transaction.web.rest;

//import com.azrul.ebanking.transaction.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

@RestController
@RequestMapping("/api/transaction-kafka")
public class TransactionKafkaResource {

    @Value("${kafka.deposit-debit-request-topic}")
    private String depositDebitRequestTopic;

    @Value("${kafka.deposit-debit-response-topic}")
    private String depositDebitResponseTopic;
    
    @Autowired
    ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(TransactionKafkaResource.class);

    public TransactionKafkaResource() {
    }

    @PostMapping("/debit")
    public TransactionResult publish(@RequestParam String ammount) throws ExecutionException, InterruptedException {
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", depositDebitRequestTopic, "AMMOUNT", ammount);
//        SendResult<String,String> result = kafkaTemplate.send(new ProducerRecord<>(topic, key, message)).get();
//        RecordMetadata metadata = result.getRecordMetadata();
//       return new PublishResult(metadata.topic(), metadata.partition(), metadata.offset(), Instant.ofEpochMilli(metadata.timestamp()));
        // set reply topic in header
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(depositDebitRequestTopic,"AMMOUNT",ammount);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, depositDebitResponseTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();

        //print all headers
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        // get consumer record
        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
        // return consumer value
        return new TransactionResult(consumerRecord.value(),ammount);
    }

    private static class TransactionResult {

        public final String balance;
        public final String transactionAmmount;

        private TransactionResult(String balance, String transactionAmmount) {
            this.balance = balance;
            this.transactionAmmount = transactionAmmount;
        }
    }
}
