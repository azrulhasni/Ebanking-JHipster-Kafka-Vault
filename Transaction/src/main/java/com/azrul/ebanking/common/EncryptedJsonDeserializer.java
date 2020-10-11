/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author azrul
 */
//@Component
public class EncryptedJsonDeserializer extends JsonDeserializer{
    //@Autowired
    VaultTemplate vaultTemplate;
    
    public EncryptedJsonDeserializer() {
        super();
        //SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    public EncryptedJsonDeserializer(VaultTemplate vaultTemplate, Class targetType) {
        super(targetType);
        this.vaultTemplate=vaultTemplate;
        //SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    

    /**
     * A constructor that additionally specifies some {@link DeserializationFeature}
     * for the deserializer
     *
     * @param deserializationFeatures the specified deserialization features
     * @param jsonNodeFactory the json node factory to use.
     */
   
    
    
    

    /**
     * A constructor that additionally specifies some {@link DeserializationFeature}
     * for the deserializer
     *
     * @param deserializationFeatures the specified deserialization features
     * @param jsonNodeFactory the json node factory to use.
     */
   
    
    @Override
    public JsonNode deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;
        
        String encryptedDataStr = new String(bytes, StandardCharsets.UTF_8);
        VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
        String dataStr = transitOperations.decrypt("my-encryption-key",encryptedDataStr);
        byte[] data = Base64.getDecoder().decode(dataStr);
        return (JsonNode) super.deserialize(topic, data);
    }
}
