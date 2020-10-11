/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.depositaccount.config;

import com.azrul.ebanking.common.EncryptedJsonDeserializer;
import com.azrul.ebanking.common.EncryptedJsonSerializer;
import com.azrul.ebanking.common.dto.Transaction;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.vault.core.VaultTemplate;

/**
 *
 * @author azrul
 */

//Added
@Configuration
class KafkaConfig {

    @Value("${kafka.bootstrap-servers:kafka.local:9092}")
    private String bootstrapServers;

    @Value("${kafka.deposit-debit-response-topic:deposit-debit-response}")
    private String depositDebitResponseTopic;

    @Value("${kafka.consumer.group.id:transaction}")
    private String groupId;
    
    @Autowired
    VaultTemplate vaultTemplate;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        //       StringSerializer.class);
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        //        EncryptedJsonSerializer.class);
        return props;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EncryptedJsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return props;
    }

    @Bean
    public ConsumerFactory<String, Transaction> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new EncryptedJsonDeserializer(vaultTemplate, Transaction.class));
    }

    @Bean
    public ProducerFactory<String, Transaction> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new EncryptedJsonSerializer(vaultTemplate));
    }

    @Bean
    public ReplyingKafkaTemplate<String, Transaction, Transaction> replyKafkaTemplate(ProducerFactory<String, Transaction> pf, KafkaMessageListenerContainer<String, Transaction> container) {
        ReplyingKafkaTemplate replyTemplate = new ReplyingKafkaTemplate<>(pf, container);
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public KafkaMessageListenerContainer<String, Transaction> replyContainer(ConsumerFactory<String, Transaction> cf) {
        ContainerProperties containerProperties = new ContainerProperties(depositDebitResponseTopic);
        containerProperties.setGroupId(groupId);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }

    @Bean
    public KafkaTemplate<String, Transaction> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Transaction>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }

}

