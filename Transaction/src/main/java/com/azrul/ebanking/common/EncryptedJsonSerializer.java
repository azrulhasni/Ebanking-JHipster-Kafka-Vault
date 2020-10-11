/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Base64;
import javax.annotation.Nullable;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author azrul
 */
//@Component
public class EncryptedJsonSerializer<T> extends JsonSerializer<T> {
	//@Autowired
        VaultTemplate vaultTemplate;
        
        public EncryptedJsonSerializer(){
            //SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this); 
        }
        
        
        public EncryptedJsonSerializer(VaultTemplate vaultTemplate) {
            this.vaultTemplate=vaultTemplate;
        }
   	
	@Override
	@Nullable
	public byte[] serialize(String topic, Headers headers, @Nullable T data) {
                byte[] dataByte = super.serialize(topic, headers, data);
                String dataStr = Base64.getEncoder().encodeToString(dataByte);
                VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
                String encryptedDataStr = transitOperations.encrypt("my-encryption-key", dataStr);
                return encryptedDataStr.getBytes();
	}

	@Override
	@Nullable
	public byte[] serialize(String topic, @Nullable T data) {
		return super.serialize(topic, data);
	}

}
