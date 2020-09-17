/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.transaction.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 *
 * @author azrul
 */
@Configuration
class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

     @Value("${kafka.deposit-debit-response-topic}")
    private String depositDebitResponseTopic;
     
     @Value("${kafka.consumer.group.id}")
    private String groupId;


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return props;
    }
//
//  @Bean
//  public ProducerFactory<String, String> producerFactory() {
//    return new DefaultKafkaProducerFactory<>(producerConfigs());
//  }
//
//  @Bean
//  public ReplyingKafkaTemplate<String, String, String> kafkaTemplate() {
//    return new ReplyingKafkaTemplate<String, String, String>(producerFactory());
//  }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyKafkaTemplate(ProducerFactory<String, String> pf, KafkaMessageListenerContainer<String, String> container) {
        ReplyingKafkaTemplate replyTemplate =  new ReplyingKafkaTemplate<>(pf, container);
       //replyTemplate.
          replyTemplate.setSharedReplyTopic(true);
          return replyTemplate;
    }

    @Bean
    public KafkaMessageListenerContainer<String, String> replyContainer(ConsumerFactory<String, String> cf) {
        ContainerProperties containerProperties = new ContainerProperties(depositDebitResponseTopic);
        containerProperties.setGroupId(groupId);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }

   

    
}
