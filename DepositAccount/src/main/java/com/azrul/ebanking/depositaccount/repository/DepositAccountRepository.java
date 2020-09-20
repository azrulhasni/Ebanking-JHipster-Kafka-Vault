package com.azrul.ebanking.depositaccount.repository;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DepositAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {
}
