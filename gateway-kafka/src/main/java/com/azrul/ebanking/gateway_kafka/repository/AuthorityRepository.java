package com.azrul.ebanking.gateway_kafka.repository;

import com.azrul.ebanking.gateway_kafka.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
